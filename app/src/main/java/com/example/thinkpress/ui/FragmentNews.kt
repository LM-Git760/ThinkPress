package com.example.thinkpress.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.thinkpress.R
import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsResult
import com.example.thinkpress.databinding.FragmentFragmentNewsBinding

class FragmentNews : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private var binding: FragmentFragmentNewsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragmentNewsBinding.inflate(inflater, container, false)

        // Erstelle eine Instanz von NewsApiService
        val newsApiService = NewsApiService.create()

        // Hier sind die zusätzlichen Parameter für die NewsViewModelFactory
        val apiKey = "pub_310178ef71a1b033f97594bf39bee90edfc10"
        val query = "Israel, Krieg, Gaza"

        // Übergebe die Instanz von NewsApiService und die zusätzlichen Parameter an die NewsViewModelFactory
        val factory = NewsViewModelFactory(newsApiService, apiKey, query)

        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)

        val adapter = NewsAdapter()
        binding?.newsRV?.adapter = adapter

        viewModel.newsResult.observe(viewLifecycleOwner, Observer { newsResult ->
            when (newsResult) {
                is NewsResult.Success -> {
                    adapter.updateData(newsResult.articles)
                    Log.i("NewsAdapter", newsResult.articles.toString())
                }
                is NewsResult.Failure -> {
                    Log.i("NewsAdapter", newsResult.code.toString())
                }
            }
        })

        viewModel.fetchNews()

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
