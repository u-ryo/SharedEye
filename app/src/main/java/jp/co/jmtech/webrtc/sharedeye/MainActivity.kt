package jp.co.jmtech.webrtc.sharedeye

import android.app.Activity
import android.content.Context
import android.net.wifi.WifiManager
import android.os.AsyncTask
import android.os.Bundle
import android.view.WindowManager
import fi.iki.elonen.NanoHTTPD
import kotlinx.android.synthetic.main.activity_main.activity_main_webview

import org.xwalk.core.XWalkPreferences
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLServerSocketFactory

class MainActivity : Activity() {
    lateinit var formattedIpAddress: String
    val port = BuildConfig.PORT
    val timeout = TimeUnit.SECONDS.toMillis(BuildConfig.TIMEOUT).toInt()
    var server: NanoHTTPD? = null
    lateinit var handler : WebHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true)
        activity_main_webview.load(
                "file:///android_asset/www/broadcaster.html", null)
        handler = WebHandlerImpl(this)
        activity_main_webview.addJavascriptInterface(handler, "handler")

        formattedIpAddress = getIp()
    }

    fun getIp() : String {
        val wifiManager =
                getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipAddress = wifiManager.connectionInfo.ipAddress
        return String.format("%d.%d.%d.%d",
                ipAddress and 0xff,
                ipAddress shr 8 and 0xff,
                ipAddress shr 16 and 0xff,
                ipAddress shr 24 and 0xff)
    }

    private fun getKeystoreFactory(passphrase: CharArray)
            : SSLServerSocketFactory {
        val keystore = KeyStore.getInstance(KeyStore.getDefaultType())
        val keystoreStream = resources.openRawResource(R.raw.keystore)
        keystore.load(keystoreStream, passphrase)
        val factory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm())
        factory.init(keystore, passphrase)
        return NanoHTTPD.makeSSLSocketFactory(keystore, factory)
    }

    override fun onResume() {
        super.onResume()
        if (server != null) {
            activity_main_webview.resumeTimers()
            activity_main_webview.onShow()
            server?.start(timeout, false)
        } else {
            val factory = getKeystoreFactory(BuildConfig.PASSWORD.toCharArray())
            object : AsyncTask<Unit, Unit, Unit>() {
                override fun doInBackground(vararg p0: Unit?) {
                    server = SignalingHttpServer(port, handler)
                    server?.makeSecure(factory, null)
                    server?.start(timeout, false)
                }
            }.execute()
        }
    }

    override fun onPause() {
        super.onPause()
        activity_main_webview.pauseTimers()
        activity_main_webview.onHide()
        server!!.stop()
    }
}
