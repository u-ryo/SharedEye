package jp.co.jmtech.webrtc.mywebrtc

import android.util.Log
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import java.io.IOException


/**
 * Created by u-ryo on 16/10/14.
 */

class SignalingHttpServer(port: Int, listener: WebListener): NanoWSD(port) {
    private val webSockets = mutableListOf<WebSocket>()
    val port: Int = port
    val listener: WebListener = listener

    override fun serve(session: IHTTPSession) : Response {
        when (session.uri) {
            "/" -> {
                listener.getOffer()
                return newFixedLengthResponse(
                        listener.getFile("www/client.html")
                                .replace("ws://localhost:8888",
                                        "ws://" + listener.getIp()
                                                + ":" + port))
            }
            "/ws" -> return super.serve(session)
            "/offer" -> return NanoHTTPD.newFixedLengthResponse(
                    Response.Status.OK, "application/json",
                    listener.getOffer())
            "/answer" -> {
                try {
                    val contentLength =
                            session.headers["content-length"]?.toInt()
                    val b = ByteArray(if (contentLength == null) 0 else contentLength.toInt())
                    session.inputStream.read(b, 0, b.size)
                    val body = String(b)
//                    val body = session.inputStream.readBytes(
//                            if (contentLength == null) 0 else contentLength.toInt()).toString()
//                val body = session.inputStream
//                        .reader(Charsets.UTF_8)
//                        .readText()
                    listener.setAnswer(body)
                    return NanoHTTPD.newChunkedResponse(
                            Response.Status.NO_CONTENT,
                            "text/plain", null)
                } catch (e : Exception) {
                    e.printStackTrace()
                    return NanoHTTPD.newFixedLengthResponse(e.toString())
                }
            }
            else -> return NanoHTTPD.newChunkedResponse(
                    Response.Status.NO_CONTENT,
                    "text/plain", null)
        }
    }

    override fun openWebSocket(handshake: IHTTPSession) : WebSocket {
        return object : WebSocket(handshake) {
            override fun onOpen() {
                webSockets.add(this)
            }

            override fun onClose(code: WebSocketFrame.CloseCode,
                                 reason: String,
                                 initiatedByRemote: Boolean) {
                webSockets.remove(this)
            }
            override fun onMessage(message: WebSocketFrame) {
                webSockets
                        .filter { ws -> ws != this && ws.isOpen }
                        .forEach { ws ->
                            message.setUnmasked()
                            ws.sendFrame(message)
                        }
            }

            override fun onException(exception: IOException) {
                Log.d("SignalingHttpServer", "Exception:", exception)
            }

            override fun onPong(pong: WebSocketFrame) {}
        }
    }
}
