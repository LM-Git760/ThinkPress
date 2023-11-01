package com.example.thinkpress.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.thinkpress.api.Article
import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsResult
import com.example.thinkpress.databinding.FragmentFragmentNewsBinding
import com.example.thinkpress.remote.FavoriteArticlesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentNews : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentFragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFragmentNewsBinding.inflate(inflater, container, false)

        val newsApiService = NewsApiService.create()
        val favoriteArticlesRepository = context?.let { FavoriteArticlesRepository(it) }

        if (favoriteArticlesRepository == null) {
            Log.e("FragmentNews", "Kontext oder FavoriteArticlesRepository ist null")
            return binding.root
        }

        val apiKey = "pub_310178ef71a1b033f97594bf39bee90edfc10"
        val factory = NewsViewModelFactory(newsApiService, favoriteArticlesRepository, apiKey)

        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]


        val adapter = NewsAdapter(viewModel, viewModel.viewModelScope)
        binding.newsRV.adapter = adapter

        // Observer für die LiveData im ViewModel
        viewModel.newsResult.observe(viewLifecycleOwner) { newsResult ->
            when (newsResult) {
                is NewsResult.Success -> {
                    adapter.updateData(newsResult.articles)
                }

                is NewsResult.Failure -> {
                    Log.e("NewsAdapter", newsResult.code.toString())
                }
            }
        }

        // Daten abrufen
        viewModel.fetchNews()



        fun onFavoriteButtonClick(article: Article) {
            viewModel.viewModelScope.launch {
                val isFavorite = viewModel.isFavorite(article)
                if (isFavorite) {
                    viewModel.removeFavorite(article)
                } else {
                    viewModel.addFavorite(article)
                }
            }
        }



    return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        return
    }
}

