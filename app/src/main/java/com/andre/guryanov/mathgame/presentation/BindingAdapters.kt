package com.andre.guryanov.mathgame.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.andre.guryanov.mathgame.R
import com.andre.guryanov.mathgame.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequireAnswers(textView: TextView, count: Int) {
    textView.text =  textView.context
        .getString(R.string.f_game_finished__needle_right_answers)
        .format(count)
}

@BindingAdapter("yourScore")
fun bindYourScore(textView: TextView, count: Int) {
    textView.text =  textView.context
        .getString(R.string.f_game_finished__your_score)
        .format(count)
}

@BindingAdapter("requirePercent")
fun bindRequirePercent(textView: TextView, count: Int) {
    textView.text =  textView.context
        .getString(R.string.f_game_finished__needle_percent_of_right_answers)
        .format(count)
}

@BindingAdapter("yourPercent")
fun bindYourPercent(textView: TextView, result: GameResult) {
    textView.text =  textView.context
        .getString(R.string.f_game_finished__percent_of_right_answers)
        .format(calculatePercent(result))
}

@BindingAdapter("winner")
fun bindWinner(imageView: ImageView, winState: Boolean) {
    val img = if (winState) R.drawable.img_win
    else R.drawable.img_lose
    imageView.setImageResource(img)
}

private fun calculatePercent(result: GameResult) : Int {
    if (result.countOfQuestions == 0) return 0
    return result.countOfRightAnswers * 100 / result.countOfQuestions
}

