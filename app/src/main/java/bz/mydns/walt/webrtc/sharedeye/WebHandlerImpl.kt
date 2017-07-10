package bz.mydns.walt.webrtc.sharedeye

import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.activity_main_webview
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
        val ip = activity.getIp()
        val message = if (ip == "0.0.0.0") {
            "After connecting to WIFI, press \"RESTART\" below."
        } else {
            "https://" + ip + ":" + activity.port + "/"
        }
        return message
    }

    override fun getOffer() : String {
        return offerSdp
                .replace(Regex("^\"(.*)\"$"), "$1")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\")
    }

    override fun setAnswer(sdp: String) {
        Handler(Looper.getMainLooper()).post {
            activity.activity_main_webview.evaluateJavascript(
                    "setAnswer($sdp);makeOffer()", null)
        }
    }

    override fun setImage(imageData: String) {
        Log.d("imageData:", imageData)
        Handler(Looper.getMainLooper()).post {
            activity.activity_main_webview.evaluateJavascript(
                    "setImage(\"$imageData\")", null)
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