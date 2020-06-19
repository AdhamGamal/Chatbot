package com.amg.chatbot

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.amg.chatbot.retrofit.models.Result
import com.bumptech.glide.Glide

@BindingAdapter("text")
fun text(textView: TextView, result: Result?) {
    if (result != null) {
        if (result.AbstractText.isNotEmpty()) textView.text =
            result.AbstractText else textView.visibility = View.GONE
    }
}

@BindingAdapter("related_topic", "image")
fun relatedTopic(textView: TextView, result: Result?, image: ImageView) {
    if (result != null) {
        val topics = result.RelatedTopics
        if (!topics.isNullOrEmpty() && result.AbstractText.isEmpty()) {
            textView.text = topics[0].Text
            if (topics[0].Icon.URL.isNotEmpty()) {
                Glide.with(image.context).load(topics[0].Icon.URL).into(image)
                image.visibility = View.VISIBLE
            }
        } else if (result.AbstractText.isNotEmpty()) {
            textView.visibility = View.GONE
        } else {
            textView.text = textView.context.getString(R.string.no_related_topics)
        }
    }
}

@BindingAdapter("load_image")
fun loadImage(image: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) Glide.with(image.context).load(url).into(image)
    else image.visibility = View.GONE
}