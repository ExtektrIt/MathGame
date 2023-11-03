package com.andre.guryanov.mathgame.domain.repository

import com.andre.guryanov.mathgame.domain.entity.GameSettings
import com.andre.guryanov.mathgame.domain.entity.Level
import com.andre.guryanov.mathgame.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ) : Question

    fun getGameSettings(level: Level): GameSettings
}