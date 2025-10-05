package com.example.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chat.ui.theme.ChatTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Log Flow Example for Image Messaging:
 *
 * Camera: Launching camera
 * Camera: Image captured successfully
 * SupabaseStorage: Starting image upload for URI: ...
 * SupabaseStorage: Generated filename: ...
 * SupabaseStorage: Image bytes size: ...
 * SupabaseStorage: Upload successful
 * SupabaseStorage: Generated public URL: ...
 * ChatViewModel: Sending image message with URL: ...
 * ChatViewModel: Sending message: Message(...)
 * ChatViewModel: Received new message
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatTheme {
                MainApp()
            }
        }
    }
}
