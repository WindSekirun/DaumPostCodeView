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
import pyxis.uzuki.live.richutilskt.utils.isEmpty

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
    private var postUrl = ""

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    /**
     * 콜백 설정
     *
     * 클릭한 우편번호, 주소 (도로명 or 지번), 건물 이름이 반환됩니다.
     */
    fun setCallback(callback: (String, String, String) -> Unit) {
        this.callback = callback
    }

    /**
     * 콜백 설정
     *
     * 클릭한 우편번호, 주소 (도로명 or 지번), 건물 이름이 반환됩니다.
     */
    fun setCallback(callback: F3<String, String, String>) {
        this.callback = { zoneCode, address, buildingName -> callback.invoke(zoneCode, address, buildingName) }
    }

    /**
     * URL 설정
     *
     * 현재 Github Pages로 임시 호스팅되고 있습니다. (언제 내려갈지 모릅니다.)
     * https://github.com/WindSekirun/DaumPostCodeView/blob/gh-pages/index.html 내 html를 다른 호스팅에
     * 삽입하신 다음, 본 메서드에 통과시키시면 됩니다.
     */
    fun setPostUrl(url: String) {
        this.postUrl = url
    }

    /**
     * 로드를 시작합니다.
     */
    fun startLoad() {
        if (postUrl.isEmpty()) {
            throw IllegalArgumentException("postUrl is empty, please setting postUrrl by setPostUrl(String url)")
        }

        webView.loadUrl(postUrl)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        View.inflate(context, R.layout.code_view, this)

        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        if (Build.VERSION.SDK_INT >= 21) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
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
    }

    private fun nativeCall(uri: Uri) {
        val zoneCode = uri.getQueryParameter("zoneCode")
        val address = uri.getQueryParameter("address")
        val buildingName = uri.getQueryParameter("buildingName")

        callback(zoneCode, address, buildingName)

        startLoad()
    }
}
