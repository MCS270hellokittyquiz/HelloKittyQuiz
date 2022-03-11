package com.example.hellokittyquiz

import Question
import android.app.Activity
import android.content.ClipData.newIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton = findViewById(R.id.cheat_button)

        fun checkAnswer(userAnswer: Boolean) {
            if (quizViewModel.getCorrect() == null) {
                var answerString: Int
                val correctAnswer = quizViewModel.currentQuestionAnswer

                if (userAnswer == correctAnswer) {
                    answerString = R.string.correct_toast
                    quizViewModel.setCorrect(true)
                } else {
                    answerString = R.string.incorrect_toast
                    quizViewModel.setCorrect(false)
                }

                if (quizViewModel.getCheater()) {
                    answerString = R.string.judgment_toast
                }

                val toast = Toast.makeText(this, answerString, Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP, 0, 0)
                toast.show()
            } else {
                var answerString = R.string.already_answered

                if (quizViewModel.getCheater()) {
                    answerString = R.string.judgment_toast
                }

                val toast = Toast.makeText(this, answerString, Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP, 0, 0)
                toast.show()
            }


            var correctQ = 0
            for (i in quizViewModel.correct) {
                if (i == null)
                    return
                else if (i == true)
                    correctQ += 1
            }
            val score = "Final score: " + (correctQ.toDouble()/quizViewModel.questionBank.size) * 100 + "% correct"
            val toast = Toast.makeText(this, score, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        }

        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestions()
        }
        previousButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestions()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestions()
        }

        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        updateQuestions()
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false) {
                quizViewModel.setCheater()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestions(){
        val id = quizViewModel.currentQuestionText
        questionTextView.setText(id)
    }
}