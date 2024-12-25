package com.sca.kotlinchattingapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.sca.kotlinchattingapp.UserChats.UserModel
import com.sca.kotlinchattingapp.R
import com.sca.kotlinchattingapp.Utils.FirebaseUtils
import com.sca.kotlinchattingapp.Views.Activities.ChattingActivity
import de.hdodenhof.circleimageview.CircleImageView

class RecentChatAdapter(
    val context: Context,
    var users: List<UserModel>
) : RecyclerView.Adapter<RecentChatAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        val userImg: CircleImageView = view.findViewById(R.id.user_img)
        val lastMsg: TextView = view.findViewById(R.id.item_last_msg)
        val lastMsgTime: TextView = view.findViewById(R.id.item_last_msg_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chats, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user = users[position]
        if(user.id==FirebaseUtils.auth.currentUser!!.uid) {
            holder.name.text = "${user.name} (You)"
            holder.lastMsg.text = "You: ${user.about}"
        }else {
            holder.name.text = user.name
            holder.lastMsg.text = user.about
        }

        Glide.with(context).load(user.profilePic)
            .placeholder(R.drawable.ic_profile_circle)
            .into(holder.userImg)



        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChattingActivity::class.java)
            intent.putExtra("userId", user.id)
            intent.putExtra("name", user.name)
            intent.putExtra("phoneNumber", user.phone)
            context.startActivity(intent)

        }

    }

    fun updateUsers(newUsers: List<UserModel>) {
        users = newUsers
        notifyDataSetChanged() // Notify the RecyclerView of the data change
    }

    override fun getItemCount(): Int = users.size
}
