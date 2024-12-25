package com.sca.kotlinchattingapp.Views.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.sca.kotlinchattingapp.UserChats.UserModel
import com.sca.kotlinchattingapp.R
import com.sca.kotlinchattingapp.Utils.FirebaseUtils
import com.sca.kotlinchattingapp.Views.MainActivity
import com.sca.kotlinchattingapp.databinding.ActivityCompleteProfileBinding

class CompleteProfileActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_IMAGE_PICKER = 101
    }

    private lateinit var binding: ActivityCompleteProfileBinding

    private var userModel: UserModel = UserModel()
    private var selectedImageUri: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityCompleteProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Set up the profile image click listener
        binding.profileImg.setOnClickListener {
            // Launch the ImagePicker to select a profile image
            ImagePicker.with(this)
                .cropSquare()
                .compress(1024)
                .maxResultSize(512, 512)
                .createIntent { intent -> startActivityForResult(intent, REQUEST_IMAGE_PICKER) }
        }

        // OnClick on SubmitBtn
        binding.submitBtn.setOnClickListener {
            val aboutText = binding.userAbout.text.toString().trim()

            if (aboutText.isEmpty() || selectedImageUri == null) {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Updating the userModel
            userModel.apply {
                profilePic = selectedImageUri ?: ""
                about = aboutText
            }
            saveUserData(userModel)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                selectedImageUri = uri.toString()

                // Loading the selected image
                Glide.with(this)
                    .load(selectedImageUri)
                    .into(binding.profileImg)
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Image selection canceled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserData(userModel: UserModel) {
        // Using a map to update user data
        val updatedData = mapOf(
            "profilePic" to userModel.profilePic,
            "about" to userModel.about
        )

        // Updating Firestore Data
       FirebaseUtils.usersReference(FirebaseUtils.auth.currentUser!!.uid)
            .update(updatedData)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error updating profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


}
