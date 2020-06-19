package com.amg.chatbot

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.amg.chatbot.databinding.ActivityMainBinding
import com.amg.chatbot.databinding.AnswerBinding
import com.amg.chatbot.databinding.ErrorBinding
import com.amg.chatbot.databinding.QuestionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.answer.observe(this, Observer { answer ->
            if (answer != null) {
                val answerView = AnswerBinding.inflate(layoutInflater)
                answerView.answer = answer
                answerView.card.setOnClickListener {
                    if (answer.AbstractText.isNotEmpty()) {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(answer.AbstractURL))
                        startActivity(browserIntent)

                    } else if (!answer.RelatedTopics.isNullOrEmpty()) {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(answer.RelatedTopics[0].FirstURL))
                        startActivity(browserIntent)
                    }
                }
                binding.list.addView(answerView.root)
                binding.listContainer.post {
                    binding.listContainer.fullScroll(View.FOCUS_DOWN)
                }
            }
        })
        viewModel.error.observe(this, Observer {
            if (!it.isNullOrBlank()) {
                val errorView = ErrorBinding.inflate(layoutInflater)
                errorView.error.text = getString(R.string.check_network)
                binding.list.addView(errorView.root)
                binding.listContainer.post {
                    binding.listContainer.fullScroll(View.FOCUS_DOWN)
                }
            }
        })

        binding.questionLabel.setEndIconOnClickListener {
            val question = binding.question.text.toString()
            if (question.isNotEmpty()) {

                val questionView = QuestionBinding.inflate(layoutInflater)
                questionView.question.text = question
                binding.list.addView(questionView.root)

                viewModel.getAnswer(question)

                hideKeyboard(it)
                binding.question.setText("")

                binding.listContainer.post {
                    binding.listContainer.fullScroll(View.FOCUS_DOWN)
                }
            }
        }

    }

    private fun hideKeyboard(view: View) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            view.windowToken,
            0
        )
    }
}