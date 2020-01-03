package com.yawjenn.mvvmpractice.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.databinding.WebViewFragmentBinding
import com.yawjenn.mvvmpractice.util.obtainFragmentViewModel
import kotlinx.android.synthetic.main.web_view_fragment.*


class WebViewFragment : Fragment() {

    private lateinit var binding: WebViewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.web_view_fragment, container, false)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = obtainFragmentViewModel(WebViewViewModel::class.java)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpWebView()
    }

    private fun setUpWebView(){
        wvWebView.run {
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true

            webViewClient = object : WebViewClient(){

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    binding.viewModel?.onPageFinishedLoading()
                }
            }

            loadUrl(arguments?.getString(PAGE_URL))
        }
    }

    companion object {
        const val PAGE_URL = "PAGE_URL"

        fun newInstance(pageUrl: String) : WebViewFragment{
            return WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(PAGE_URL, pageUrl)
                }
            }
        }
    }
}
