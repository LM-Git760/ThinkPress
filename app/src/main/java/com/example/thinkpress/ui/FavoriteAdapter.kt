package com.example.thinkpress.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thinkpress.R
import com.example.thinkpress.api.Favorite

class FavoriteAdapter : ListAdapter<Favorite, FavoriteAdapter.FavoriteViewHolder>(
    FavoriteDiffCallback()
) {

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)

        fun bind(favorite: Favorite) {
            titleTextView.text = favorite.title
            descriptionTextView.text = favorite.descriptor  // Bemerkung: Hier wurde 'descriptor' statt 'description' verwendet
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
    }

    class FavoriteDiffCallback : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.articleId == newItem.articleId
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }
    }
}

