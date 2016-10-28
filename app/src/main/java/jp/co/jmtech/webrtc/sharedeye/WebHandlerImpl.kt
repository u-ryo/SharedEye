package jp.co.jmtech.webrtc.sharedeye

import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.xwalk.core.JavascriptInterface
import org.xwalk.core.XWalkView

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
        val ip = activity.getIp()
        val message = if (ip == "0.0.0.0") {
            "Please connect using WIFI."
        } else {
            "https://" + ip + ":" + activity.port + "/"
        }
        return message
    }

    @JavascriptInterface
    fun reload() {
        Handler(Looper.getMainLooper()).post {
            activity.activity_main_webview.reload(XWalkView.RELOAD_IGNORE_CACHE)
        }
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
                    "setAnswer(" + sdp + ");makeOffer()", null)
        }
    }

    override fun setImage(imageData: String) {
        Log.d("imageData:", imageData)
        Handler(Looper.getMainLooper()).post {
            activity.activity_main_webview.evaluateJavascript(
                    "setImage(\"" + imageData + "\")", null)
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