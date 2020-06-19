package com.amg.chatbot

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.amg.chatbot.retrofit.Repository
import com.amg.chatbot.retrofit.models.Result

class ChatViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    val answer: LiveData<Result>
        get() = repository.answer

    val error: LiveData<String>
        get() = repository.error

    fun getAnswer(query: String) {
        repository.getAnswer(query)
    }
}