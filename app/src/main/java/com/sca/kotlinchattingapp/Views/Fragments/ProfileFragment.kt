package com.sca.kotlinchattingapp.Views.Fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sca.kotlinchattingapp.Models.Repositories.AuthRepository
import com.sca.kotlinchattingapp.UserChats.UserModel
import com.sca.kotlinchattingapp.R
import com.sca.kotlinchattingapp.Utils.FirebaseUtils
import com.sca.kotlinchattingapp.Views.Activities.LoginActivity
import com.sca.kotlinchattingapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val repository = AuthRepository()
    private lateinit var imageLauncher: ActivityResultLauncher<Intent>
//    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
                result?.let {
                    if (it.resultCode == Activity.RESULT_OK) {
                        val data = it.data
                        val imageUri = data?.data
                        if (imageUri != null) {

                        }
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        getSelfInfo()

        binding.logoutText.setOnClickListener {
            FirebaseUtils.auth.signOut()
            val intent = Intent(requireContext(),LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            Toast.makeText(requireContext(), "You have been logged out...", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

    private fun getSelfInfo() {
        repository.getSelfInfo(FirebaseUtils.auth.currentUser!!.uid, onSuccess = { me ->

            setUserData(me)

        }, onFailure = { error ->
            Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_LONG).show()
        })
    }

    private fun setUserData(me: UserModel) {

        binding.profileName.setText(me.name)
        binding.profilePhone.setText(me.phone)
        binding.profileAbout.setText(me.about)
       Glide.with(requireContext()).load(me.profilePic).placeholder(R.drawable.ic_profile_circle).into(binding.profileImg)

    }
}
