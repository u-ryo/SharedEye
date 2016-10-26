package jp.co.jmtech.webrtc.mywebrtc

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
                    val contentLength =
                            session.headers["content-length"]?.toInt()
                    val b = ByteArray(if (contentLength == null) 0 else contentLength.toInt())
                    session.inputStream.read(b, 0, b.size)
                    val body = String(b)
                    handler.setAnswer(body)
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
}
