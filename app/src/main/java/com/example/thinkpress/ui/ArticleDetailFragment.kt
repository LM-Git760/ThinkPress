package com.example.thinkpress.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thinkpress.api.Article
import com.example.thinkpress.databinding.FragmentArticleDetailBinding
import com.squareup.picasso.Picasso

class ArticleDetailFragment : Fragment() {

    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = arguments!!.getSerializable("article") as Article
        binding.titleTextView.text = article.title
        binding.pubDateTextView.text = article.pubDate
        binding.descriptionTextView.text = article.description
        binding.contentTextView.text = article.content
        Picasso.get().load(article.imageUrl).into(binding.articleImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
