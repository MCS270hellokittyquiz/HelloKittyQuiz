package com.example.hellokittyquiz

import Question
import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    val questionBank = listOf(
        Question(R.string.question1, true),
        Question(R.string.question2, false),
        Question(R.string.question3, false),
        Question(R.string.question4, true)
    )

    var currentIndex = 0
    var isCheater = Array(questionBank.size){false}
    var correct = Array<Boolean?>(questionBank.size){null}

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textReId

    fun setCheater() {
        isCheater[currentIndex] = true
    }

    fun getCheater(): Boolean {
        return isCheater[currentIndex]
    }

    fun setCorrect(wasCorrect: Boolean) {
        correct[currentIndex] = wasCorrect
    }

    fun getCorrect(): Boolean? {
        return correct[currentIndex]
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1).mod(questionBank.size)
    }

    fun reset() {
        currentIndex = 0
        isCheater = Array(questionBank.size){false}
        correct = Array<Boolean?>(questionBank.size){null}
    }
}