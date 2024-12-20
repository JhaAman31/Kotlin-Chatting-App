package com.sca.kotlinchattingapp.Views.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.Auth
import com.sca.kotlinchattingapp.Adapters.RecentChatAdapter
import com.sca.kotlinchattingapp.Models.Repositories.AuthRepository
import com.sca.kotlinchattingapp.Utils.FirebaseUtils

import com.sca.kotlinchattingapp.ViewModels.ChatViewModel

import com.sca.kotlinchattingapp.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val repository=AuthRepository()
    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var adapter: RecentChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        binding = FragmentChatBinding.inflate(inflater)


//        getAndSetOtherUserData()
        // Inflate the layout for this fragment
        return binding.root
    }

//    private fun getAndSetOtherUserData() {
//        repository.otherUserInfo()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter with a click listener
        adapter = RecentChatAdapter(requireContext(), users = listOf())

        // Set up RecyclerView with the adapter and layout manager
        binding.recentChatsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recentChatsRecycler.adapter = adapter

        // Observe ViewModel's user list and update the adapter
        chatViewModel.users.observe(viewLifecycleOwner) { users ->
            adapter.updateUsers(users)
        }

        // Fetch all users from the ViewModel
        chatViewModel.fetchAllUsers()
    }
}