package com.example.thinkpress.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsResult
import com.example.thinkpress.databinding.FragmentFragmentNewsBinding

class FragmentNews : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private var binding: FragmentFragmentNewsBinding? = null

    companion object {
        fun newInstance() = FragmentNews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragmentNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Erstelle eine Instanz von NewsApiService
        val newsApiService = NewsApiService.create()

        // Übergebe die Instanz von NewsApiService an die NewsViewModelFactory
        val factory = NewsViewModelFactory(newsApiService)

        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)

        val adapter = NewsAdapter()
        binding?.newsRV?.adapter = adapter

        viewModel.newsResult.observe(viewLifecycleOwner, Observer { newsResult ->
            when (newsResult) {
                is NewsResult.Success -> {
                    adapter.updateData(newsResult.articles)
                    Log.i("NewsAdapter",newsResult.articles.toString() )
                }
                is NewsResult.Failure -> {
                    Log.i("NewsAdapter", newsResult.code.toString())
                }
            }
        }
        )

        viewModel.fetchNews()
    }
}
