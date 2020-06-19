package com.amg.chatbot.retrofit

import androidx.lifecycle.MutableLiveData
import com.amg.chatbot.retrofit.models.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val service: DuckApiService) {

    val answer = MutableLiveData<Result>(null)
    val error = MutableLiveData<String>(null)

    fun getAnswer(question: String) {
        service.getAnswer(question).enqueue(object : Callback<Result?> {
            override fun onFailure(call: Call<Result?>, exception: Throwable) {
                error.value = exception.message
            }

            override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                answer.value = response.body()
            }
        })

    }
}