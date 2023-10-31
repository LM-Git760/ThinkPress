package com.example.thinkpress.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thinkpress.R
import com.example.thinkpress.api.Article
import com.example.thinkpress.remote.FavoriteArticlesRepository
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val repository: FavoriteArticlesRepository) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var articleList: List<Article> = emptyList()

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
        val articleImageView: ImageView = itemView.findViewById(R.id.article_image_view)
        val favoriteCheckBox: CheckBox = itemView.findViewById(R.id.favorite_button)
        val dateTextView : TextView = itemView.findViewById(R.id.pub_date_text_view)
    }

    fun submitList(newList: List<Article>) {
        articleList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val article = articleList[position]
        holder.titleTextView.text = article.title
        holder.descriptionTextView.text = article.description
        holder.dateTextView.text = article.pubDate
        Picasso.get().load(article.imageUrl).into(holder.articleImageView)

        holder.favoriteCheckBox.setOnCheckedChangeListener(null)  // Verhindert, dass alte Listener aufgerufen werden
        holder.favoriteCheckBox.isChecked = true  // Alle Artikel in dieser Liste sind Favoriten

        holder.favoriteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                repository.removeFavorite(article)
            }
        }
    }
}
