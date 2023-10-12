package com.example.thinkpress

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.thinkpress.databinding.ActivityMainBinding
import com.example.thinkpress.ui.FragmentNews
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle Home navigation
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_search -> {
                // Handle Search navigation
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                // Handle Profile navigation
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        // Überprüfe, ob das Fragment bereits hinzugefügt wurde, um zu verhindern,
        // dass es bei Konfigurationsänderungen wie Drehungen erneut hinzugefügt wird
        if (savedInstanceState == null) {
            // Füge das FragmentNews zum FragmentManager hinzu
            supportFragmentManager.commit {
                replace(R.id.fragment_container, FragmentNews.newInstance())
            }
        }
    }
}
