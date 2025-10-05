package com.example.chat.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.model.Channel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(): ViewModel( ){
 
    private val firebaseDatabase = Firebase.database

    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()

    init {
        getChannels()
    }

    private fun getChannels(){
        viewModelScope.launch {
            firebaseDatabase.getReference("channel").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Channel>()
                    task.result.children.forEach { data: DataSnapshot ->
                        val channel = Channel(
                            data.key ?: "",
                            data.value.toString()
                        )
                        list.add(channel)
                    }
                    _channels.value = list
                }
            }
        }
    }

    fun addChannel(name: String) {
        val key = firebaseDatabase.getReference("channel").push().key
        firebaseDatabase.getReference("channel").child(key!!).setValue(name).addOnCompleteListener {
            getChannels()
        }
    }
}