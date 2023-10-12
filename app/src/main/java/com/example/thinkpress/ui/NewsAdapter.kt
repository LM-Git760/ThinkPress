package com.example.thinkpress.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thinkpress.R
import com.example.thinkpress.api.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var articles: List<Article> = listOf()

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
        // ... andere UI-Elemente
    }

    fun updateData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        Log.d("NewsAdapter", "Binding article at position $position: $article")
        holder.titleTextView.text = article.title ?:"Kein Titel Verfügbar"
        holder.descriptionTextView.text = article.description ?:"Keine Überschrift Verfügbar"
    }

    override fun getItemCount(): Int {
        Log.d("NewsAdapter", "getItemCount called, size: ${articles.size}")
        return articles.size
    }

}
