package com.sca.kotlinchattingapp.Views

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sca.kotlinchattingapp.R
import com.sca.kotlinchattingapp.Utils.FirebaseUtils
import com.sca.kotlinchattingapp.ViewPagerAdapter
import com.sca.kotlinchattingapp.Views.Activities.LoginActivity
import com.sca.kotlinchattingapp.Views.Fragments.ChatFragment
import com.sca.kotlinchattingapp.Views.Fragments.ProfileFragment
import com.sca.kotlinchattingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter :ViewPagerAdapter

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
//        binding.appBtn.setOnClickListener {
//            FirebaseUtils.auth.signOut()
//            startActivity(Intent(this, LoginActivity::class.java))
//        }


        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        // Synchronize TabLayout and ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Chats"
                1 -> tab.text = "Status"
                2 -> tab.text = "Profile"
            }
        }.attach()

        // Handle tab and page selection
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)?.select()
            }
        })
    }

}


//
//        binding.bottomNavigation.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.chat_menu -> {
//                    loadFragment(ChatFragment())
//                    true
//                }
//
//                R.id.profile_menu -> {
//                    loadFragment(ProfileFragment())
//                    true
//                }
//
//                else -> false
//            }
//        }
//
//        if (savedInstanceState == null) {
//            loadFragment(ChatFragment())
//        }
//    }
//
//
//    private fun loadFragment(fragment: Fragment) {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragment_container, fragment)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
//
//    }
//}
