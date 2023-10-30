package com.example.thinkpress.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thinkpress.R
import com.example.thinkpress.api.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsAdapter(
    private val viewModel: NewsViewModel,
    private val coroutineScope: CoroutineScope // Übergeben Sie den CoroutineScope aus dem Fragment
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var onArticleClickListener: OnArticleClickListener? = null
    private var articles: MutableList<Article> = mutableListOf()

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favoriteButton: Button = itemView.findViewById(R.id.favorite_button)
        val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
        val articleImageView: ImageView = itemView.findViewById(R.id.article_image_view)
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

        holder.titleTextView.text = article.title
        holder.descriptionTextView.text = article.description

        coroutineScope.launch {
            val isFavorite = viewModel.isFavorite(article)
            holder.favoriteButton.text = if (isFavorite) "Favorisiert" else "Favorisieren"
        }

        holder.favoriteButton.setOnClickListener {
            coroutineScope.launch {
                if (viewModel.isFavorite(article)) {
                    viewModel.removeFavoriteFromDatabase(article)
                    holder.favoriteButton.text = "Favorisieren"
                } else {
                    viewModel.addFavorite(article)
                    holder.favoriteButton.text = "Favorisiert"
                }
            }
        }

        holder.itemView.setOnClickListener {
            onArticleClickListener?.onArticleClick(article)
        }

        Glide.with(holder.itemView.context)
            .load(article.imageUrl)
            .encodeQuality(85)
            .placeholder(com.google.android.material.R.drawable.abc_star_black_48dp)
            .into(holder.articleImageView)
    }
}
