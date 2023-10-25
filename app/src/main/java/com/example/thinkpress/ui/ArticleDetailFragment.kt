package com.example.thinkpress.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thinkpress.R
import com.example.thinkpress.api.Article

class ArticleDetailFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val article = arguments?.getSerializable("article") as? Article
        if (article == null) {
            Log.e("ArticleDetailFragment", "Keine Artikelinformationen erhalten")
            return null
        }

        // Hier können Sie Ihre UI-Elemente mit den Artikelinformationen füllen

        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

}
