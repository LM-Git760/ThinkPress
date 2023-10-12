package com.example.thinkpress.ui

import android.graphics.Bitmap
import android.util.Log
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
import com.example.thinkpress.remote.FavoriteArticlesRepository

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

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
        holder.titleTextView.text = article.title ?: "Kein Titel Verfügbar"
        holder.descriptionTextView.text = article.description ?: "Keine Überschrift Verfügbar"
        // Favoritenstatus überprüfen
        if (FavoriteArticlesRepository.FavoriteArticlesRepository.isFavorite(article)) {
            holder.favoriteButton.text = "★"
        } else {
            holder.favoriteButton.text = "☆"
        }

        holder.favoriteButton.setOnClickListener {
            if (FavoriteArticlesRepository.isFavorite(article)) {
                FavoriteArticlesRepository.removeFavorite(article)
                holder.favoriteButton.text = "☆"
            } else {
                FavoriteArticlesRepository.addFavorite(article)

            }
            // Lade das Bild mit Glide
            if (article.imageUrl != null) {
            } else {
                Glide.with(holder.itemView.context)
                    .load(com.google.android.material.R.drawable.abc_star_black_48dp) // Füge deinen Platzhalter hier ein
                    .into(holder.articleImageView)
            }
            Glide.with(holder.itemView.context)
                .load(article.imageUrl)
                .encodeQuality(85)
                .placeholder(com.google.android.material.R.drawable.abc_star_black_48dp)
                .into(holder.articleImageView)


        }


    }
}
