package com.sca.kotlinchattingapp.Views.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sca.kotlinchattingapp.Models.Repositories.AuthRepository
import com.sca.kotlinchattingapp.R
import com.sca.kotlinchattingapp.Views.MainActivity

class SplashActivity : AppCompatActivity() {
    private val repository=AuthRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Handler(Looper.getMainLooper()).postDelayed({
            isUserLoggedIn()
        }, 2000)
    }

    private fun isUserLoggedIn() {
        if(repository.isLoggedIn())
            startActivity(Intent(this, MainActivity::class.java))
        else
            startActivity(Intent(this,LoginActivity::class.java))
    }

}