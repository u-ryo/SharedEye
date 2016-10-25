package jp.co.jmtech.webrtc.mywebrtc

import android.os.Handler
import android.os.Looper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

/**
 * Created by u-ryo on 16/10/21.
 */
class WebListenerImpl(activity: MainActivity) : WebListener {
    val activity: MainActivity = activity
    lateinit var offerSdp: String
    val wait: Long = 2

    init {
        Thread.sleep(TimeUnit.SECONDS.toMillis(wait))
        Handler(Looper.getMainLooper()).post {
            activity.activity_main_webview.evaluateJavascript(
                    "makeOffer()", null)
        }
        Thread.sleep(TimeUnit.SECONDS.toMillis(wait))
        Handler(Looper.getMainLooper()).post {
            activity.activity_main_webview.evaluateJavascript(
                    "getOfferSdp();",
                    { sdp -> offerSdp = sdp })
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