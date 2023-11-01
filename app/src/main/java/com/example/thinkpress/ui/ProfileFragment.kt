package com.example.thinkpress.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thinkpress.R
import com.example.thinkpress.api.Article
import com.example.thinkpress.databinding.FragmentProfileBinding
import com.example.thinkpress.remote.FavoriteArticlesRepository

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var favoriteArticlesAdapter: FavoriteAdapter
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)


        val favoriteArticlesRepository = context?.let { FavoriteArticlesRepository(it) }
        if (favoriteArticlesRepository!= null) {
            viewModel = ViewModelProvider(
                this,
                ProfileViewModel.ProfileViewModelFactory(favoriteArticlesRepository)
            )[ProfileViewModel::class.java]
            Log.d("ProfileFragment", "ViewModel initialized")
        } else {
            Log.e("ProfileFragment", "FavoriteArticlesRepository is null")
        }

        favoriteArticlesAdapter = FavoriteAdapter(favoriteArticlesRepository!!)
        binding.recyclerView.apply {
            adapter = favoriteArticlesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.favoriteArticles.observe(viewLifecycleOwner) { articles ->
            Log.d("ProfileFragment", "Received articles: $articles")
            favoriteArticlesAdapter.submitList(articles)
        }

        return binding.root
    }

    fun onArticleClicked(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", this.getSerializable(article.toString()))
        }
        val fragment = ArticleDetailFragment().apply {
            arguments = bundle
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ProfileFragment", "onDestroyView called")
    }
}
