package jp.co.jmtech.webrtc.mywebrtc

import android.os.Handler
import android.os.Looper
import kotlinx.android.synthetic.main.activity_main.*
import org.xwalk.core.JavascriptInterface

/**
 * Created by u-ryo on 16/10/21.
 */
class WebHandlerImpl(val activity: MainActivity) : WebHandler {
    lateinit var offerSdp: String

    @JavascriptInterface
    fun setOffer(offerSdp: String) {
        this.offerSdp = offerSdp
    }

    @JavascriptInterface
    fun getUrl() : String {
        return "https://" + activity.formattedIpAddress +
                ":" + activity.port + "/"
    }

    override fun getOffer(): String {
        return offerSdp
                .replace(Regex("^\"(.*)\"$"), "$1")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\")
    }

    override fun setAnswer(sdp: String) {
        Handler(Looper.getMainLooper()).post {
            activity.activity_main_webview.evaluateJavascript(
                    "setAnswer(" + sdp + ")", null)
        }
    }

    override fun getIp(): String {
        return activity.formattedIpAddress
    }

    override fun getFile(name: String): String {
        return activity.assets.open(name)
                .reader(Charsets.UTF_8)
                .readText()
    }
}