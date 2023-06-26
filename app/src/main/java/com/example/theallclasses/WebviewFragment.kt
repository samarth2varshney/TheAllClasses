package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.theallclasses.databinding.FragmentWebviewBinding

class WebviewFragment : Fragment() {

    private lateinit var binding: FragmentWebviewBinding
    var URL:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            URL = it.getString("formlink")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebviewBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView = binding.webView
        // Enable JavaScript (optional)
        webView.settings.javaScriptEnabled = true
        // Set a WebViewClient to handle page navigation and loading
        webView.webViewClient = WebViewClient()
        // Load a web page
        webView.loadUrl(URL!!)
    }

    companion object {
        fun onBackPressed(): Boolean {
            return true
        }
    }

}