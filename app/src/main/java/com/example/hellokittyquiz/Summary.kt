package com.example.hellokittyquiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val EXTRA_PERCENT_CORRECT = "com.example.hellokittyquiz.percent_correct"

private lateinit var percentTextView: TextView
private lateinit var retakeButton: Button

class Summary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.summary_layout)
    }

    companion object {
        fun newIntent(packageContext: Context, percentCorrect: Double): Intent {
            return Intent(packageContext, Summary::class.java).apply {
                putExtra(EXTRA_PERCENT_CORRECT, percentCorrect)
            }
        }
    }
}