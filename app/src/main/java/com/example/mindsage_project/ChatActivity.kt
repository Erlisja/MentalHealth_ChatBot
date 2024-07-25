//package com.example.mindsage_project
//
//import android.os.Bundle
//import android.widget.EditText
//import android.widget.ImageButton
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class ChatActivity : AppCompatActivity() {
//
//    private lateinit var chatAdapter: ChatAdapter
//    private val messages = mutableListOf<ChatMessage>()
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var sendButton: ImageButton
//    private lateinit var editText: EditText
//
//    private lateinit var ollamaClient: OllamaClient
//
//    private val mentalHealthKeywords = listOf(
//        "anxiety", "depression", "stress", "mental health", "well-being", "mindfulness",
//        "therapy", "counseling", "panic attack", "PTSD", "bipolar disorder", "schizophrenia",
//        "OCD", "trauma", "self-esteem", "mental illness", "psychiatry", "psychology",
//        "emotion", "mood", "disorder", "suicide", "support group", "meditation", "self-care",
//        "coping mechanisms", "burnout", "addiction", "substance abuse", "recovery", "grief",
//        "loneliness", "resilience", "stigma", "resources", "help"
//    )
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.chat_screen)
//
//        ollamaClient = OllamaClient()
//
//        recyclerView = findViewById(R.id.chat_recycler_view)
//        sendButton = findViewById(R.id.send_button)
//        editText = findViewById(R.id.input_text)
//
//        chatAdapter = ChatAdapter(messages)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = chatAdapter
//
//        sendButton.setOnClickListener {
//            val userInput = editText.text.toString()
//            if (userInput.isNotBlank()) {
//                addMessage(ChatMessage(userInput, false))
//                if (isMentalHealthQuery(userInput)) {
//                    sendMessageToOllama(userInput)
//                } else {
//                    addMessage(ChatMessage("I can only answer mental health-related questions.", true))
//                }
//                editText.text.clear()
//            }
//        }
//    }
//
//    private fun addMessage(chatMessage: ChatMessage) {
//        messages.add(chatMessage)
//        chatAdapter.notifyItemInserted(messages.size - 1)
//        recyclerView.scrollToPosition(messages.size - 1)
//    }
//
//    private fun isMentalHealthQuery(prompt: String): Boolean {
//        return mentalHealthKeywords.any { keyword -> prompt.contains(keyword, ignoreCase = true) }
//    }
//
//    private fun sendMessageToOllama(prompt: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                ollamaClient.streamResponse(
//                    prompt,
//                    onResponse = { responseFragment ->
//                        CoroutineScope(Dispatchers.Main).launch {
//                            val lastMessageIndex = messages.size - 1
//                            if (lastMessageIndex >= 0 && messages[lastMessageIndex].isBot) {
//                                messages[lastMessageIndex].text += responseFragment
//                                chatAdapter.notifyItemChanged(lastMessageIndex)
//                            } else {
//                                addMessage(ChatMessage(responseFragment, true))
//                            }
//                        }
//                    },
//                    onComplete = {
//                        // Handle completion if needed
//                    },
//                    onError = { e ->
//                        CoroutineScope(Dispatchers.Main).launch {
//                            addMessage(ChatMessage("Error: ${e.message}", true))
//                        }
//                    }
//                )
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    addMessage(ChatMessage("Error: ${e.message}", true))
//                }
//            }
//        }
//    }
//
//}

package com.example.mindsage_project

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// The main activity for the chat interface
class ChatActivity : AppCompatActivity() {

    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<ChatMessage>()
    private val ollamaClient = OllamaClient()
    private lateinit var recyclerView: RecyclerView
    private lateinit var sendButton: ImageButton
    private lateinit var editText: EditText
    private lateinit var menuButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_screen)

        recyclerView = findViewById(R.id.chat_recycler_view)
        sendButton = findViewById(R.id.send_button)
        editText = findViewById(R.id.input_text)
        menuButton = findViewById(R.id.menu_icon)

        chatAdapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter


        menuButton.setOnClickListener {
            val intent = Intent(this, SideBarActivity::class.java)
            startActivity(intent)
        }

        sendButton.setOnClickListener {
            val userInput = editText.text.toString()
            if (userInput.isNotBlank()) {
                addMessage(ChatMessage(userInput, false)) // Add user message
                val prompt = "You are a mental health assistant. Only answer questions related to mental health. If a question is unrelated, respond with 'I can only answer mental health-related questions.'\n\nUser: $userInput"
                sendMessageToOllama(prompt)
                editText.text.clear() // Clear the input field
            }
        }
    }

    private fun addMessage(chatMessage: ChatMessage) {
        messages.add(chatMessage)
        chatAdapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun sendMessageToOllama(prompt: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                ollamaClient.streamResponse(
                    prompt,
                    onResponse = { responseFragment ->
                        CoroutineScope(Dispatchers.Main).launch {
                            val lastMessageIndex = messages.size - 1
                            if (lastMessageIndex >= 0 && messages[lastMessageIndex].isBot) {
                                messages[lastMessageIndex].text += responseFragment
                                chatAdapter.notifyItemChanged(lastMessageIndex)
                            } else {
                                addMessage(ChatMessage(responseFragment, true))
                            }
                        }
                    },
                    onComplete = {

                    },
                    onError = { e ->
                        CoroutineScope(Dispatchers.Main).launch {
                            addMessage(ChatMessage("Error: ${e.message}", true))
                        }
                    }
                )
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    addMessage(ChatMessage("Error: ${e.message}", true))
                }
            }
        }
    }

}
