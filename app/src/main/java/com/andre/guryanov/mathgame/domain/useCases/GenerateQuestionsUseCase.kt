package com.andre.guryanov.mathgame.domain.useCases

import com.andre.guryanov.mathgame.domain.entity.Question
import com.andre.guryanov.mathgame.domain.repository.GameRepository

class GenerateQuestionsUseCase(
    private val repo: GameRepository
) {

    operator fun invoke(maxSumValue: Int): Question {
        return repo.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {

        private const val COUNT_OF_OPTIONS = 6
    }
}