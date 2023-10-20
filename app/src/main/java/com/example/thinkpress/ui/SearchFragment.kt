package com.example.thinkpress.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsResult
import com.example.thinkpress.databinding.FragmentSearchBinding

// Definition der SearchFragment Klasse, die von Fragment erbt.
class SearchFragment : Fragment() {

    // Verwendung von View Binding für eine sicherere und effizientere Handhabung von Views.
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    // Verwendung des `viewModels` Delegierten zur Initialisierung von `viewModel`
    private val viewModel: NewsViewModel by viewModels()

    // Definition der onCreateView Methode zur Inflation des Layouts für dieses Fragment.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Definition der onViewCreated Methode, die aufgerufen wird, nachdem die Ansicht erstellt wurde.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Erstellung des NewsAdapter und Zuweisung zum RecyclerView.
        val adapter = NewsAdapter(viewModel)
        binding.recyclerView.adapter = adapter

        // Beobachtung der newsResult LiveData und Aktualisierung des Adapters bei Änderungen.
        viewModel.newsResult.observe(viewLifecycleOwner, { newsResult ->
            when (newsResult) {
                is NewsResult.Success -> {
                    adapter.updateData(newsResult.articles)
                }
                is NewsResult.Failure -> {
                    // Fehlerbehandlung
                }
            }
        }
        )

        // Definition des OnQueryTextListener für die SearchView.
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchNews(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optional: Behandlung von Änderungen des Abfragetexts
                return true
            }
        }
        )
    }

    // Definition der onDestroyView Methode zur Aufhebung der Bindung.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
