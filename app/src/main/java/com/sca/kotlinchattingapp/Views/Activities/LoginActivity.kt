package com.sca.kotlinchattingapp.Views.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sca.kotlinchattingapp.Models.Repositories.AuthRepository
import com.sca.kotlinchattingapp.R
import com.sca.kotlinchattingapp.ViewModels.AuthViewModel
import com.sca.kotlinchattingapp.Views.MainActivity
import com.sca.kotlinchattingapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()
    private val repo = AuthRepository()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setInProgress(false)
        binding.loginBtn.setOnClickListener {
            onLoginBtnClick()
        }

        binding.newUser.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    private fun onLoginBtnClick() {
        val email = binding.loginEmail.text.toString().trim()
        val password = binding.loginPwd.text.toString().trim()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            setInProgress(true)
            viewModel.login(email, password, onSuccess = { user ->

                setInProgress(false)
                startActivity(Intent(this, CompleteProfileActivity::class.java))
                Toast.makeText(
                    this,
                    "${user.name} have become a part of our family",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }, onFailure = { error ->
                setInProgress(false)
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            })
        } else {
            Toast.makeText(this, "Enter valid email and password", Toast.LENGTH_SHORT).show()
        }
    }

    fun setInProgress(inProgress: Boolean) {
        if (inProgress) {
            binding.loginBtn.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.loginBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
}