package com.example.thinkpress.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thinkpress.R
import com.example.thinkpress.api.Article
import kotlinx.coroutines.launch



interface OnArticleClickListener {
    fun onArticleClick(article: Article)
}

class NewsAdapter(private val viewModel: NewsViewModel) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
     var onArticleClickListener: OnArticleClickListener? = null

    private var articles: MutableList<Article> = mutableListOf()

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favoriteButton: Button = itemView.findViewById(R.id.favorite_button)
        val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
        val articleImageView: ImageView = itemView.findViewById(R.id.article_image_view)
        // ... andere UI-Elemente
    }

    fun updateData(newArticles: MutableList<Article>) {
        val uniqueArticles = newArticles.distinctBy { it.articleId }
        this.articles.clear()
        this.articles.addAll(uniqueArticles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        Log.d("NewsAdapter", "Binding article at position $position: $article")
        holder.titleTextView.text = article.title
        holder.descriptionTextView.text = article.description

        // Setze den Favoriten-Button-Text basierend auf dem Favoritenstatus
        viewModel.viewModelScope.launch {
            val isFavorite = viewModel.isFavorite(article.articleId)
            holder.favoriteButton.text = if (isFavorite) "★" else "☆"
        }

        holder.itemView.setOnClickListener {
            viewModel.viewModelScope.launch {
                onArticleClickListener?.onArticleClick(article)
            }
        }

        // Lade das Bild mit Glide
        Glide.with(holder.itemView.context)
            .load(article.imageUrl)  // Verwendet den Platzhalter, wenn imageUrl null ist
            .encodeQuality(85)
            .placeholder(com.google.android.material.R.drawable.abc_star_black_48dp)
            .into(holder.articleImageView)
    }
}
