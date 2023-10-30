package com.example.thinkpress.ui

// ProfileFragment.kt
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thinkpress.R
import com.example.thinkpress.api.Favorite
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private lateinit var favoriteArticlesAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteRecyclerView: RecyclerView = view.findViewById(R.id.recycler_fav)
        favoriteRecyclerView.layoutManager = LinearLayoutManager(context)

        favoriteArticlesAdapter = FavoriteAdapter()
        favoriteRecyclerView.adapter = favoriteArticlesAdapter

        val favoriteArticlesRef = FirebaseDatabase.getInstance().getReference("favoriteArticles")
        favoriteArticlesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoriteArticles = snapshot.children.mapNotNull { it.getValue(Favorite::class.java) }
                favoriteArticlesAdapter.submitList(favoriteArticles)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ProfileFragment", "Database error: ${error.message}")
            }
        }
        )
    }
}
