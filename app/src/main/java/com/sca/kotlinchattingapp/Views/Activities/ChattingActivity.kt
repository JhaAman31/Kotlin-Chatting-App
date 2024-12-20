package com.sca.kotlinchattingapp.Views.Activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.sca.kotlinchattingapp.Adapters.MessageAdapter
import com.sca.kotlinchattingapp.Models.MessageModels
import com.sca.kotlinchattingapp.Models.Repositories.AuthRepository
import com.sca.kotlinchattingapp.R
import com.sca.kotlinchattingapp.Utils.FirebaseUtils
import com.sca.kotlinchattingapp.ViewModels.MessageViewModel
import com.sca.kotlinchattingapp.databinding.ActivityChattingBinding

class ChattingActivity : AppCompatActivity() {

    private val repository = AuthRepository()

    private lateinit var binding: ActivityChattingBinding
    private lateinit var messageAdapter: MessageAdapter
    private val messageViewModel: MessageViewModel by viewModels()
    private var currentUserId: String = FirebaseUtils.auth.currentUser!!.uid
    private lateinit var otherUserId: String
    private lateinit var chatRoomId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        messageAdapter = MessageAdapter(currentUserId)

        binding.messageRecycler.layoutManager = LinearLayoutManager(this)
        binding.messageRecycler.adapter = messageAdapter

//        Other User data from their Id
        otherUserId = intent.getStringExtra("userId").orEmpty()
        if (otherUserId.isNotEmpty()) {
//            creating chatroomId
            chatRoomId = "${currentUserId}__${otherUserId}"
            Log.d("CHAT_ROOM_ID", "onCreate: " + chatRoomId)
            messageViewModel.loadMessages(chatRoomId)
        } else {
            Toast.makeText(this, "Chat room ID is invalid", Toast.LENGTH_LONG).show()
        }

        // Loading messages from Firestore
        if (chatRoomId.isNotEmpty()) {
            messageViewModel.loadMessages(chatRoomId)
        } else {
            Toast.makeText(this, "Chat room ID is invalid", Toast.LENGTH_LONG).show()
        }

//        Observing the messages
        messageViewModel.messages.observe(this) { messages ->
            messageAdapter.submitList(messages)
        }

//        On Send Btn Click
        binding.sendBtn.setOnClickListener {
            val messageText = binding.messageWriter.text.toString()
            if (messageText.isNotEmpty()) {
                val message = MessageModels(
                    senderId = currentUserId,
                    message = messageText,
                    lastMsgTime = Timestamp.now()
                )
                messageViewModel.sendMessage(chatRoomId, message)
                binding.messageWriter.text.clear()
            } else
                Toast.makeText(this, "Enter some message first", Toast.LENGTH_LONG).show()
        }


        getOtherUserInfo(otherUserId)
    }

    private fun getOtherUserInfo(otherUserId: String) {

        repository.otherUserInfo(otherUserId, onSuccess = { otherUser ->
            if (otherUser.name == FirebaseUtils.auth.currentUser!!.uid)
                binding.otherUserName.text = "${otherUser.name} (You)"
            else
                binding.otherUserName.text = otherUser.name

            Glide.with(this).load(otherUser.profilePic)
                .placeholder(R.drawable.ic_profile_circle)
                .into(binding.otherUserImg)


        }, onFailure = { error ->
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            Log.d("GetOtherUserInfo", "getOtherUserInfo: " + error.localizedMessage)

        })

    }
}