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
import com.sca.kotlinchattingapp.R
import com.sca.kotlinchattingapp.ViewModels.AuthViewModel
import com.sca.kotlinchattingapp.Views.MainActivity
import com.sca.kotlinchattingapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setInProgress(false)

        binding.registerBtn.setOnClickListener {
            onRegisterBtnClick()
        }

        binding.alreadyBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun onRegisterBtnClick() {
        val number = binding.userPhone.text.trim().toString()
        val userName = binding.userName.text.trim().toString()
        val userPwd = binding.userPwd.text.trim().toString()
        val userEmail = binding.userEmail.text.trim().toString()

        if (number.isNotEmpty() && userName.isNotEmpty() && userEmail.isNotEmpty() && userPwd.isNotEmpty()) {
            setInProgress(true)
            viewModel.register(userName, userEmail, number, userPwd,"", onSuccess = {
                setInProgress(false)
                Toast.makeText(
                    this,
                    "You have been registered successfully. Verify your email!",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, onFailure = { error ->
                setInProgress(false)
                Toast.makeText(
                    this,
                    error.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            })
        } else {
            Toast.makeText(this, "Enter valid email and password", Toast.LENGTH_SHORT).show()
        }

    }

    fun setInProgress(inProgress:Boolean){
        if(inProgress){
            binding.registerBtn.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.registerBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

}

