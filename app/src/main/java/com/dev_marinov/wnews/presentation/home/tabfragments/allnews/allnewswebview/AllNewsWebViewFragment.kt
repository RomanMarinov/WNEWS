package com.dev_marinov.geniussonglyrics.presentation.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebResourceRequest
import androidx.fragment.app.Fragment
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import com.dev_marinov.wnews.R
import com.dev_marinov.wnews.databinding.FragmentWebViewAllNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("SetJavaScriptEnabled")
@AndroidEntryPoint
class AllNewsWebViewFragment : Fragment() {
    private lateinit var binding: FragmentWebViewAllNewsBinding
    lateinit var allNewsWebViewViewModel: AllNewsWebViewViewModel
    private lateinit var callback: OnBackPressedCallback
    private val args: AllNewsWebViewFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.e("444", "зашел в FragmentWebView")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_view_all_news, container, false)
        allNewsWebViewViewModel = ViewModelProvider(requireActivity())[AllNewsWebViewViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSettingsWebView()
        addUrlToStack()
        loadUrl()
        setWebViewClient()
        onBackPressedCallback()
    }

    private fun addUrlToStack(){
        if (!allNewsWebViewViewModel.urlListStack.isEmpty()) {
            if (allNewsWebViewViewModel.urlListStack.size == 1
                && allNewsWebViewViewModel.urlListStack.peek().toString() != args.url) {
                allNewsWebViewViewModel.urlListStack.clear()
                allNewsWebViewViewModel.urlListStack.add(args.url)
                Log.e("444", "arguments?.let IF it.getString(KEY_URL )" + args.url)
                Log.e("444", "arguments?.let IF")
            }
        } else {
            allNewsWebViewViewModel.urlListStack.add(args.url)
        }
    }

    private fun loadUrl(){
        binding.webView.loadUrl(allNewsWebViewViewModel.urlListStack.peek())
        Log.e("444", "arguments?.let размер urlListStack.size=" + allNewsWebViewViewModel.urlListStack.size +
                " содержимое urlListStack=" + allNewsWebViewViewModel.urlListStack)
    }

    private fun setWebViewClient(){
        binding.webView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.e("444", "----------------- onPageStarted -----------------")
                Log.e("444", "onPageStarted сейчас ты видишь =" +  binding.webView.url)
                allNewsWebViewViewModel.backStatus = true
                callback.isEnabled = allNewsWebViewViewModel.urlListStack.size != 1

                super.onPageStarted(view, url, favicon)
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                Log.e("444","=shouldOverrideUrlLoading request добавил в urlListStack.add =" + request.url.toString())
                allNewsWebViewViewModel.urlListStack.add(request.url.toString())
                Log.e("444","=shouldOverrideUrlLoading размер urlListStack.size=" + allNewsWebViewViewModel.urlListStack.size +
                        " содержимое urlListStack=" + allNewsWebViewViewModel.urlListStack)
                return true
            }
        }
    }

    private fun onBackPressedCallback() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.e("444", "нажал OnBackPressedCallback")
                // можно удалить только после того как сработает onPageStarted
                if (allNewsWebViewViewModel.backStatus) {
                    Log.e("444", "OnBackPressedCallback удалил последний элемент")
                    allNewsWebViewViewModel.urlListStack.pop() // удалить последний элемент
                    allNewsWebViewViewModel.backStatus = false
                }

                if (!allNewsWebViewViewModel.urlListStack.isEmpty()) {
                    binding.webView.loadUrl(allNewsWebViewViewModel.urlListStack.peek()) // загрузить последний элемент списка
                    Log.e("444", "OnBackPressedCallback размер urlListStack.size=" + allNewsWebViewViewModel.urlListStack.size +
                            " содержимое urlListStack=" + allNewsWebViewViewModel.urlListStack)
                } else {
                    callback.remove()
                }

                if(allNewsWebViewViewModel.urlListStack.size == 1) {
                    Log.e("444", "OnBackPressedCallback callback.remove urlListStack.size=" + allNewsWebViewViewModel.urlListStack.size)
                    callback.remove()
                    allNewsWebViewViewModel.urlListStack.clear()
                    Log.e("444", "OnBackPressedCallback размер ПОСЛЕ CLEAR urlListStack.size=" + allNewsWebViewViewModel.urlListStack.size +
                            " содержимое urlListStack=" + allNewsWebViewViewModel.urlListStack)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setSettingsWebView(){
        binding.webView.settings.javaScriptEnabled = true // поддержка джава скрипт для работы с сайтами
        binding.webView.settings.allowContentAccess = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.useWideViewPort = true
    }
}

//    companion object {
//        val KEY_URL = "key_url"
//
//        fun createInstance(url: String) : WebViewFragment {
//
//            val fragment = WebViewFragment()
//            val bundle = Bundle()
//            bundle.putString(KEY_URL, url)
//            fragment.arguments = bundle
//            return fragment
//        }
//    }
