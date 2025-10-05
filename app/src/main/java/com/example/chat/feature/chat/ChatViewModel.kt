package com.example.chat.feature.chat

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.model.Message
import com.example.chat.SupabaseStorageUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    
   private   val _messages = MutableStateFlow<List<Message>>(emptyList())
    
    val  messages = _messages.asStateFlow()
   private val db = Firebase.database

    fun sendImageMessage (uri: Uri, channelID: String){
        viewModelScope.launch {
            val storageUtils = SupabaseStorageUtil(context)
            val downloadUri = storageUtils.uploadImage(uri)
            downloadUri?.let { imageUrl ->
                Log.d("ChatViewModel", "Sending image message with URL: $imageUrl")
                sendMessages(channelID, "", imageUrl)
            } ?: Log.d("ChatViewModel", "Failed to upload image")
        }
    }

    fun sendMessages(channelID: String, message: String, imageUrl: String? = null) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val messageObj = Message(
            db.reference.push().key ?: UUID.randomUUID().toString(),
            currentUser?.uid ?: "",
            message.takeIf { it.isNotBlank() },
            System.currentTimeMillis().toString(),
            currentUser?.displayName ?: "Anonymous",
            null,
            imageUrl
        )
        Log.d("ChatViewModel", "Sending message: $messageObj")
        db.reference.child("messages").child(channelID).push().setValue(messageObj)
    }


    fun getListenMessages(channelID: String){
        db.getReference("messages").child(channelID)
            .orderByChild("createdAt")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d("ChatViewModel", "Received new message")
                    val message = snapshot.getValue(Message::class.java)
                    message?.let {
                        val currentList = _messages.value.toMutableList()
                        currentList.add(it)
                        _messages.value = currentList
                    } ?: Log.d("ChatViewModel", "Failed to parse message")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    // Handle message updates if needed
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    // Handle message removal if needed
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    // Handle message reordering if needed
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("ChatViewModel", "Error listening to messages: ${error.message}")
                }
            })
    }
}