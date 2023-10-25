package com.example.thinkpress.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thinkpress.api.Article
import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsResult
import com.example.thinkpress.databinding.FragmentFragmentNewsBinding
import com.example.thinkpress.remote.FavoriteArticlesRepository

class FragmentNews : Fragment() {
    var onArticleClickListener: OnArticleClickListener? = null


    private lateinit var viewModel: NewsViewModel
    private var binding: FragmentFragmentNewsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragmentNewsBinding.inflate(inflater, container, false)

        // Erstelle eine Instanz von NewsApiService
        val newsApiService = NewsApiService.create()

        // Erstelle eine Instanz von FavoriteArticlesRepository
        val favoriteArticlesRepository = context?.let { FavoriteArticlesRepository(it) }

        // Überprüfe, ob die Kontext und favoriteArticlesRepository Instanzen nicht null sind
        if (favoriteArticlesRepository == null) {
            Log.e("FragmentNews", "Kontext oder FavoriteArticlesRepository ist null")
            return binding?.root
        }

        // Hier sind die zusätzlichen Parameter für die NewsViewModelFactory
        val apiKey = "pub_310178ef71a1b033f97594bf39bee90edfc10"

        // Übergebe die Instanz von NewsApiService und favoriteArticlesRepository und die zusätzlichen Parameter an die NewsViewModelFactory
        val factory = NewsViewModelFactory(newsApiService, favoriteArticlesRepository, apiKey)

        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)

        // Übergebe das viewModel an den NewsAdapter
        val adapter = NewsAdapter(viewModel)
        binding?.newsRV?.adapter = adapter

        viewModel.newsResult.observe(viewLifecycleOwner
        ) { newsResult ->
            when (newsResult) {
                is NewsResult.Success -> {
                    adapter.updateData(newsResult.articles)
                    Log.i("NewsAdapter", newsResult.articles.toString())
                }

                is NewsResult.Failure -> {
                    Log.i("NewsAdapter", newsResult.code.toString())
                }
            }
        }

        viewModel.fetchNews()

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }




}
