package com.example.hellokittyquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val EXTRA_PERCENT_CORRECT = "com.example.hellokittyquiz.percent_correct"
const val EXTRA_RETAKE_QUIZ = "com.example.hellokittyquiz.retake_quiz"

class Summary : AppCompatActivity() {

    private lateinit var percentTextView: TextView
    private lateinit var retakeButton: Button

    private var percentCorrect = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.summary_layout)

        percentTextView = findViewById(R.id.percent_correct_text)
        retakeButton = findViewById(R.id.retake_quiz_button)
        percentCorrect = intent.getIntExtra(EXTRA_PERCENT_CORRECT, 0)

        percentTextView.setText("Percent Correct: $percentCorrect%")

        retakeButton.setOnClickListener{
            retakeQuiz()
        }
    }

    private fun retakeQuiz() {
        val data = Intent().apply {
            putExtra(EXTRA_RETAKE_QUIZ, true)
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    companion object {
        fun newIntent(packageContext: Context, percentCorrect: Int): Intent {
            return Intent(packageContext, Summary::class.java).apply {
                putExtra(EXTRA_PERCENT_CORRECT, percentCorrect)
            }
        }
    }
}