//package com.example.mindsage_project
//import android.view.Gravity
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.google.android.material.animation.Positioning
//
//class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
//            return MessageViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
//            val message = messages[position]
//            holder.bind(message)
//        }
//
//        override fun getItemCount(): Int = messages.size
//
//        inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//            private val messageTextView: TextView = view.findViewById(R.id.message_text)
//
//
//            fun bind(message: ChatMessage) {
//                messageTextView.text = message.text
//                // Adjust view based on whether the message is from the bot or the user
//                if (message.isBot) {
//                    messageTextView.setBackgroundResource(R.drawable.bot_message_background)
//
//                } else {
//                    messageTextView.setBackgroundResource(R.drawable.user_message_background)
//
//                }
//            }
//        }
//    }

package com.example.mindsage_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutRes = if (viewType == MessageType.USER.ordinal) {
            R.layout.item_chat_message
        } else {
            R.layout.item_chat_message
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isBot) {
            MessageType.BOT.ordinal
        } else {
            MessageType.USER.ordinal
        }
    }

    enum class MessageType {
        USER,
        BOT
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.message_text)

        fun bind(message: ChatMessage) {
            textView.text = message.text
            // Optionally, apply different styling based on message type
            // Example: adjust margins, background colors, etc.
            if (message.isBot) {
                // Adjust layout for bot messages
                textView.setBackgroundResource(R.drawable.bot_message_background)
            } else {
                // Adjust layout for user messages
                textView.setBackgroundResource(R.drawable.user_message_background)
            }
        }
    }
}
