package jp.co.jmtech.webrtc.mywebrtc

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.URL
import java.util.HashMap
import java.util.Scanner

/**
 * Created by u-ryo on 16/10/14.
 */

class SignalingHttpServer {
    private val osMap = HashMap<String, OutputStream>()
    private val server: HttpServer = HttpServer.create(InetSocketAddress(8888), 0)

    fun startServer() {
        server.createContext("/sse") { t ->
            val responseHeaders = t.responseHeaders
            responseHeaders.add("Connection", "keep-alive")
            responseHeaders.add("Cache-Control", "no-cache")
            responseHeaders.add("Content-Type", "text/event-stream")
            t.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0)
            osMap.put(t.requestURI.query, t.responseBody)
        }

        server.createContext("/") { t ->
            val inputStream =
                    URL("file:///android_asset/www/sse_signaling_1to1_vanilla.html")
                            .openStream()
            t.responseHeaders.add("Content-Type", "text/html")
            t.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0)
            val os = t.responseBody
            val copiedBytes = inputStream.copyTo(os)
            os.flush()
            inputStream.close()
            os.close()
        }

        server.createContext("/msg") { t ->
            val id = t.requestURI.query
            val s = Scanner(t.requestBody).useDelimiter("\\A")
            val message = if (s.hasNext()) s.next() else ""
            System.err.println(String.format("from:%s,msg:%s", id, message))
            for ((key, os) in osMap) {
                if (key != id) {
                    os.write(("event: message\ndata: "
                            + message + "\n\n").toByteArray())
                    os.flush()
                }
            }
            t.sendResponseHeaders(HttpURLConnection.HTTP_NO_CONTENT, 0)
            t.responseBody.close()
        }

        server.executor = null // creates a default executor
        server.start()
    }
}
