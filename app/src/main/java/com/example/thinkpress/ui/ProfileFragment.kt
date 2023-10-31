package com.example.thinkpress.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thinkpress.api.Favorite
import com.example.thinkpress.databinding.FragmentProfileBinding
import com.example.thinkpress.remote.FavoriteArticlesRepository

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var favoriteArticlesAdapter: FavoriteAdapter
    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val favoriteArticlesRepository = context?.let { FavoriteArticlesRepository(it) }
        if (favoriteArticlesRepository != null) {
            viewModel = ViewModelProvider(this,
                ProfileViewModel.ProfileViewModelFactory(favoriteArticlesRepository)
            )[ProfileViewModel::class.java]
        } else {
            // Handle error, z.B. Log oder Toast
        }

        favoriteArticlesAdapter = FavoriteAdapter()
        binding?.recyclerFav?.adapter = favoriteArticlesAdapter
        binding?.recyclerFav?.layoutManager = LinearLayoutManager(context)

        // Beobachte die LiveData aus dem ViewModel
        if (::viewModel.isInitialized) {
            viewModel.favoriteArticles.observe(viewLifecycleOwner) { articles ->
                // Aktualisiere den Adapter
                favoriteArticlesAdapter.submitList(newList = articles)
            }
        } else {
            // Handle error, z.B. Log oder Toast
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
