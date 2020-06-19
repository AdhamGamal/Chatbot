package com.amg.chatbot.retrofit

import com.amg.chatbot.retrofit.models.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DuckApiService {
    @GET("/?format=json")
    fun getAnswer(@Query("q") query: String): Call<Result>
}

