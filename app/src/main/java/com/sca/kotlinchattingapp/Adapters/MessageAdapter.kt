package com.sca.kotlinchattingapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sca.kotlinchattingapp.Models.MessageModels
import com.sca.kotlinchattingapp.R

class MessageAdapter(private val currentUserId: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messages = listOf<MessageModels>()

    fun submitList(messages: List<MessageModels>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].senderId == currentUserId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sender_message, parent, false)
                SentMessageViewHolder(view)
            }
            VIEW_TYPE_RECEIVED -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_receiver_message, parent, false)
                ReceivedMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is SentMessageViewHolder -> holder.bind(message)
            is ReceivedMessageViewHolder -> holder.bind(message)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    // ViewHolder for sent messages
    class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(messageModel: MessageModels) {
            val sentMessageText = itemView.findViewById<TextView>(R.id.sentMessageText)
            val sentMessageTime = itemView.findViewById<TextView>(R.id.sentMessageTime)

            sentMessageText.text = messageModel.message
            sentMessageTime.text = messageModel.lastMsgTime.toString()
        }
    }

    // ViewHolder for received messages
    class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(messageModel: MessageModels) {
            val receivedMessageText = itemView.findViewById<TextView>(R.id.receivedMessage)
            val receivedMessageTime = itemView.findViewById<TextView>(R.id.receivedMessageTime)

            receivedMessageText.text = messageModel.message
            receivedMessageTime.text = messageModel.lastMsgTime.toString()
        }
    }
}
