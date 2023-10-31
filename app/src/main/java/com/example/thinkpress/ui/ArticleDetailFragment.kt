package com.example.thinkpress.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.thinkpress.api.Article
import com.example.thinkpress.databinding.FragmentArticleDetailBinding

class ArticleDetailFragment : Fragment() {

    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = arguments?.getSerializable("article") as? Article
            ?: // Log an error or show a toast to the user
            return

        binding.titleTextView.text = article.pubDate
        binding.authorTextView.text = article.title
        binding.descriptionTextView.text = article.description
        binding.contentTextView.text = article.content

        Glide.with(this)
            .load(article.imageUrl)
            .into(binding.articleImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
