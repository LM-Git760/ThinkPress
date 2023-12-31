package com.example.thinkpress

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.thinkpress.databinding.ActivityMainBinding
import com.example.thinkpress.ui.FragmentNews
import com.example.thinkpress.ui.ProfileFragment
import com.example.thinkpress.ui.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = null

        when (item.itemId) {
            R.id.navigation_home -> {
                // Handle Home navigation
                selectedFragment = FragmentNews()
            }
            R.id.navigation_dashboard -> {
                // Handle Search navigation
                selectedFragment = SearchFragment()
            }
            R.id.navigation_notifications -> {
                // Handle Profile navigation
                selectedFragment = ProfileFragment()
            }
        }
        if (selectedFragment !=null){
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, selectedFragment
            ).commit()
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Firebase initialisieren
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        FirebaseApp.initializeApp(this)




        // Set default fragment
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, FragmentNews()
        ).commit()


        binding.bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        // Überprüfe, ob das Fragment bereits hinzugefügt wurde, um zu verhindern,
        // dass es bei Konfigurationsänderungen wie Drehungen erneut hinzugefügt wird

        if (savedInstanceState == null) {
            // Füge das FragmentNews zum FragmentManager hinzu
            supportFragmentManager.commit {
                replace(R.id.fragment_container, FragmentNews())
            }
        }

    }
}
