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
        val dateTextView: TextView = itemView.findViewById(R.id.pub_date_text_view)
        val contentTextView: TextView = itemView.findViewById(R.id.content_text_view)  // Stellen Sie sicher, dass diese ID im XML-Layout existiert
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

        if (article.content != null && !article.content.isEmpty()) {
            holder.contentTextView.text = article.content
        } else {
            holder.contentTextView.text = ""  // Setzen Sie den Text auf leer, wenn kein Inhalt vorhanden ist
        }

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
