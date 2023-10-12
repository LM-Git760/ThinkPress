package com.example.thinkpress.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thinkpress.R
import com.example.thinkpress.api.Article
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteArticlesRef = FirebaseDatabase.getInstance().getReference("favoriteArticles")
        favoriteArticlesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoriteArticles = snapshot.children.mapNotNull { it.getValue(Article::class.java) }
                // Aktualisiere deine RecyclerView mit favoriteArticles
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        }
        )
    }
}