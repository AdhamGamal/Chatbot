package com.amg.chatbot.retrofit.models

data class Result(
    val AbstractText: String,
    val AbstractURL: String,
    val Image: String,
    val AbstractSource: String,
    val RelatedTopics: MutableList<RelatedTopic>
)