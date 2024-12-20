package com.sca.kotlinchattingapp.Views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.sca.kotlinchattingapp.R
import com.sca.kotlinchattingapp.Views.Fragments.ChatFragment
import com.sca.kotlinchattingapp.Views.Fragments.ProfileFragment
import com.sca.kotlinchattingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.chat_menu -> {
                    loadFragment(ChatFragment()) // Replace with your Chat Fragment
                    true
                }

                R.id.profile_menu -> {
                    loadFragment(ProfileFragment()) // Replace with your Profile Fragment
                    true
                }

                else -> false
            }
        }

        if (savedInstanceState == null) {
            loadFragment(ChatFragment()) // Default fragment
        }
    }


    private fun loadFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
}
