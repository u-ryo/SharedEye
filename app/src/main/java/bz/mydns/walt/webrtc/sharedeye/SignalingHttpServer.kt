package bz.mydns.walt.webrtc.sharedeye

import android.util.Log
import fi.iki.elonen.NanoHTTPD


/**
 * Created by u-ryo on 16/10/14.
 */

class SignalingHttpServer(val port: Int, val handler: WebHandler) : NanoHTTPD(port) {
    override fun serve(session: IHTTPSession) : Response {
        when (session.uri) {
            "/" -> {
                handler.getOffer()
                return newFixedLengthResponse(
                        handler.getFile("www/client.html")
                                .replace("ws://localhost:8888",
                                        "ws://" + handler.getIp()
                                                + ":" + port))
            }
            "/offer" -> return NanoHTTPD.newFixedLengthResponse(
                    Response.Status.OK, "application/json",
                    handler.getOffer())
            "/answer" -> {
                try {
                    handler.setAnswer(readBody(session))
                    return NanoHTTPD.newChunkedResponse(
                            Response.Status.NO_CONTENT,
                            "text/plain", null)
                } catch (e : Exception) {
                    e.printStackTrace()
                    return NanoHTTPD.newFixedLengthResponse(e.toString())
                }
            }
            "/image" -> {
                val imageData = readBody(session)
                Log.d("imagedata:", imageData)
                handler.setImage(imageData)
                return NanoHTTPD.newChunkedResponse(
                        Response.Status.NO_CONTENT,
                        "text/plain", null)
            }
            else -> return NanoHTTPD.newChunkedResponse(
                    Response.Status.NO_CONTENT,
                    "text/plain", null)
        }
    }

    private fun readBody(session: IHTTPSession) : String {
        var contentLength =
                session.headers["content-length"]?.toInt()
        if (contentLength == null) contentLength = 0
//        val b = ByteArray(if (contentLength == null) 0 else contentLength)
//        session.inputStream.read(b, 0, b.size)
//        return String(b)
        val b = ByteArray(10240)
        var readBytes = session.inputStream.read(b, 0,
                Math.min(b.size, contentLength))
        var result = ""
        while (readBytes > 0) {
            result += String(b, 0, readBytes)
            contentLength -= readBytes
            readBytes = session.inputStream.read(
                    b, 0, Math.min(b.size, contentLength))
        }
        result += String(b, 0, readBytes)
        return result
    }
}
