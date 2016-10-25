package jp.co.jmtech.webrtc.mywebrtc

import android.app.Activity
import android.content.Context
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.inaka.killertask.KillerTask
import kotlinx.android.synthetic.main.activity_main.*

import org.xwalk.core.XWalkActivity
import org.xwalk.core.XWalkPreferences
import org.xwalk.core.XWalkView

class MainActivity : Activity() {
    //public class MainActivity extends XWalkActivity {

    //    private WebView mWebView;
//    private var mXWalkView: XWalkView? = null

    //    @Override
    //    protected void onXWalkReady() {
    ////        mXWalkView.load("http://crosswalk-project.org/", null);
    //        mXWalkView.load("file:///android_asset/www/talk.html", null);
    //    }

    lateinit var formattedIpAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        //        WebSettings webSettings = mWebView.getSettings();
        //        webSettings.setJavaScriptEnabled(true);
        ////        mWebView.loadUrl("https://google.jp");
        //        mWebView.loadUrl("file:///android_asset/www/talk.html");
        //        mWebView.setWebViewClient(new WebViewClient());
//        mXWalkView = findViewById(R.id.activity_main_webview) as XWalkView
        //        mXWalkView.load("file:///android_asset/www/talk.html", null);
//        mXWalkView!!.load("http://192.168.33.176:8888/", null)
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipAddress = wifiManager.connectionInfo.ipAddress
        formattedIpAddress = String.format("%d.%d.%d.%d", ipAddress and 0xff, ipAddress shr 8 and 0xff,
                ipAddress shr 16 and 0xff, ipAddress shr 24 and 0xff)
        Toast.makeText(this, formattedIpAddress, Toast.LENGTH_LONG).show()
        KillerTask({ SignalingHttpServer(8888, WebListenerImpl(this)).start(30000, false) })
                .go()
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true)
        activity_main_webview.load("file:///android_asset/www/broadcaster.html", null)
    }

    override fun onBackPressed() {
        //        if (mWebView.canGoBack()) {
        //            mWebView.goBack();
        //        } else {
        super.onBackPressed()
        //        }
    }

    override fun onResume() {
        super.onResume()
    }

//    fun getOfferSpd() : String {
//        activity_main_webview.evaluateJavascript("")
//    }
}
