package com.dev_marinov.wnews.presentation.home.tabfragments.sport.sportwebview

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
import com.dev_marinov.wnews.databinding.FragmentWebViewSportBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("SetJavaScriptEnabled")
@AndroidEntryPoint
class SportWebViewFragment : Fragment() {
    private lateinit var binding: FragmentWebViewSportBinding
    lateinit var viewModel: SportWebViewViewModel
    private lateinit var callback: OnBackPressedCallback
    private val args: SportWebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_web_view_sport, container, false)
        viewModel = ViewModelProvider(requireActivity())[SportWebViewViewModel::class.java]

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

    private fun addUrlToStack() {
        if (!viewModel.urlListStack.isEmpty()) {
            if (viewModel.urlListStack.size == 1
                && viewModel.urlListStack.peek().toString() != args.url
            ) {
                viewModel.urlListStack.clear()
                viewModel.urlListStack.add(args.url)
            }
        } else {
            viewModel.urlListStack.add(args.url)
        }
    }

    private fun loadUrl() {
        binding.webView.loadUrl(viewModel.urlListStack.peek())
    }

    private fun setWebViewClient() {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                viewModel.backStatus = true
                callback.isEnabled = viewModel.urlListStack.size != 1

                super.onPageStarted(view, url, favicon)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                viewModel.urlListStack.add(request.url.toString())
                return true
            }
        }
    }

    private fun onBackPressedCallback() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.backStatus) {
                    viewModel.urlListStack.pop() // удалить последний элемент
                    viewModel.backStatus = false
                }

                if (!viewModel.urlListStack.isEmpty()) {
                    binding.webView.loadUrl(viewModel.urlListStack.peek()) // загрузить последний элемент списка
                } else {
                    callback.remove()
                }

                if (viewModel.urlListStack.size == 1) {
                    callback.remove()
                    viewModel.urlListStack.clear()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setSettingsWebView() {
        binding.webView.settings.javaScriptEnabled =
            true // поддержка джава скрипт для работы с сайтами
        binding.webView.settings.allowContentAccess = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.useWideViewPort = true
    }
}