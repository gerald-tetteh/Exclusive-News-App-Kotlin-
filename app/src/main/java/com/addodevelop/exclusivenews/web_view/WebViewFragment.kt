package com.addodevelop.exclusivenews.web_view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.addodevelop.exclusivenews.R
import com.addodevelop.exclusivenews.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentWebViewBinding.inflate(inflater)
        val arguments: WebViewFragmentArgs by navArgs()
        try {
            binding.webView.webViewClient = WebViewClient()
            binding.webView.loadUrl(arguments.articleUrl)
        } catch (e: Exception) {
            Log.i("WebViewFragment","Did not work ${e.message}")
        }

        return binding.root
    }

}