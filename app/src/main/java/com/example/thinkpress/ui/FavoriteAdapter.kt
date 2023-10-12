package com.example.thinkpress.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thinkpress.R
import com.example.thinkpress.api.Article

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favoriteArticles: List<Article> = listOf()

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
    }

    fun submitList(newFavoriteArticles: List<Article>) {
        favoriteArticles = newFavoriteArticles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val article = favoriteArticles[position]
        holder.titleTextView.text = article.title
        holder.descriptionTextView.text = article.description
    }

    override fun getItemCount(): Int = favoriteArticles.size
}
