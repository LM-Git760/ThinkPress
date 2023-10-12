package com.example.thinkpress.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsResult
import com.example.thinkpress.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    // Erstelle eine Instanz von NewsApiService
    val newsApiService = NewsApiService.create()
    val apiKey = "pub_310178ef71a1b033f97594bf39bee90edfc10"

    // Übergebe die Instanz von NewsApiService und die zusätzlichen Parameter an die NewsViewModelFactory
    val factory = NewsViewModelFactory(newsApiService, apiKey)
   // private val viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)

    lateinit var viewModel: NewsViewModel







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NewsAdapter()
        binding.recyclerView.adapter = adapter
        // Übergebe die Instanz von NewsApiService und die zusätzlichen Parameter an die NewsViewModelFactory
        val factory = NewsViewModelFactory(newsApiService, apiKey)
        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)

        viewModel.newsResult.observe(viewLifecycleOwner, Observer { newsResult ->
            when (newsResult) {
                is NewsResult.Success -> {
                    adapter.updateData(newsResult.articles)  // updateData ist eine Methode in deinem Adapter
                }
                is NewsResult.Failure -> {
                    // Handle failure
                }
            }
        }
        )

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    // Handle search query submission
                    viewModel.searchNews(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optional: Handle search query text change
                return true
            }
        }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
