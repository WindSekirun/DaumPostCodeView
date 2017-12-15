package pyxis.uzuki.live.daumpostcodeview

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.webkit.*
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.code_view.view.*
import pyxis.uzuki.live.richutilskt.impl.F3

/**
 * DaumPostCodeView
 * Class: DaumPostCodeView
 * Created by Pyxis on 2017-12-15.
 *
 *
 * Description:
 */

class DaumPostCodeView : LinearLayout {
    private var callback: (String, String, String) -> Unit = { _, _, _ -> }
    private val postHandler = Handler()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    fun setCallback(callback: (String, String, String) -> Unit) {
        this.callback = callback
    }

    fun setCallback(callback: F3<String, String, String>) {
        this.callback = { zoneCode, address, buildingName -> callback.invoke(zoneCode, address, buildingName) }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        View.inflate(context, R.layout.code_view, this)

        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                val uri = Uri.parse(url)
                if (uri.scheme == "native") {
                    nativeCall(uri)
                    return true
                }

                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return false
                }

                val uri: Uri? = request?.url
                if (uri != null && uri.scheme == "native") {
                    nativeCall(uri)
                    return true
                }

                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        webView.loadUrl("")
    }

    private fun nativeCall(uri: Uri) {
        val zoneCode = uri.getQueryParameter("zoneCode")
        val address = uri.getQueryParameter("address")
        val buildingName = uri.getQueryParameter("buildingName")

        callback(zoneCode, address, buildingName)
    }
}
