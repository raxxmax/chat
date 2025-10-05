package com.example.chat

import android.content.Context
import android.net.Uri
import android.util.Log
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import java.util.UUID

class SupabaseStorageUtil(val context : Context) {
    val supabase = createSupabaseClient(
        " ",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlImV4cCI6MjA3NDQ1MDc3M30.DknMDoKRijSRvcdtW-RxtsuYeWI6_pqmZ66LzDf2E2A"

    ) {
        install(Storage)
    }
    suspend fun uploadImage(uri: Uri): String? {
        try {
            Log.d("SupabaseStorage", "Starting image upload for URI: $uri")
            val extension = uri.path?.substringAfterLast(".") ?: "jpg"
            val filename = "${UUID.randomUUID()}.${extension}"
            Log.d("SupabaseStorage", "Generated filename: $filename")

            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val bytes = inputStream.readBytes()
            Log.d("SupabaseStorage", "Image bytes size: ${bytes.size}")

            supabase.storage.from(BUCKET_NAME).upload(filename, bytes)
            Log.d("SupabaseStorage", "Upload successful")

            val publicUrl = supabase.storage.from(BUCKET_NAME).publicUrl(filename)
            Log.d("SupabaseStorage", "Generated public URL: $publicUrl")

            return publicUrl
        } catch (e: Exception) {
            Log.e("SupabaseStorage", "Upload failed", e)
            e.printStackTrace()
            return null
        }
    }
    companion object{
        const val   BUCKET_NAME = "chat_image"

    }
}
