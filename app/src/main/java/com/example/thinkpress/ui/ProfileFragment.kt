package com.example.thinkpress.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thinkpress.R
import com.example.thinkpress.api.Article
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private lateinit var favoriteArticlesAdapter: FavoriteAdapter
    private lateinit var favoriteRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteRecyclerView = view.findViewById(R.id.recycler_fav) // Ersetze 'favoriteRecyclerView' durch die ID deiner RecyclerView in fragment_profile.xml
        favoriteRecyclerView.layoutManager = LinearLayoutManager(context)

        favoriteArticlesAdapter = FavoriteAdapter()
        favoriteRecyclerView.adapter = favoriteArticlesAdapter

        val favoriteArticlesRef = FirebaseDatabase.getInstance().getReference("favoriteArticles")
        favoriteArticlesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoriteArticles = snapshot.children.mapNotNull { it.getValue(Article::class.java) }
                favoriteArticlesAdapter.submitList(favoriteArticles)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
