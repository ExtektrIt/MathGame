package com.andre.guryanov.mathgame.data

import com.andre.guryanov.mathgame.domain.entity.GameSettings
import com.andre.guryanov.mathgame.domain.entity.Level
import com.andre.guryanov.mathgame.domain.entity.Question
import com.andre.guryanov.mathgame.domain.repository.GameRepository
import kotlin.math.min
import kotlin.math.max

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = (MIN_SUM_VALUE..maxSumValue).random()
        val visibleNumber = (MIN_ANSWER_VALUE until sum).random()
        val options = HashSet<Int>()
        val rightNumber = sum - visibleNumber
        options.add(rightNumber)
        val from = max(rightNumber - countOfOptions, MIN_ANSWER_VALUE)
        val to = min((maxSumValue), rightNumber + countOfOptions)
        while (options.size < countOfOptions) {
            val randomNum = (from until to).random()
            options.add(randomNum)
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> GameSettings(10, 3, 50, 10)
            Level.EASY -> GameSettings(10, 10, 70, 60)
            Level.MEDIUM -> GameSettings(20, 20, 80, 40)
            Level.HARD -> GameSettings(30, 30, 90, 40)
        }
    }
}