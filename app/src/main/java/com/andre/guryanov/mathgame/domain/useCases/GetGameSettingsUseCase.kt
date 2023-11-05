package com.andre.guryanov.mathgame.domain.useCases

import com.andre.guryanov.mathgame.domain.entity.GameSettings
import com.andre.guryanov.mathgame.domain.entity.Level
import com.andre.guryanov.mathgame.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repo: GameRepository
) {

    operator fun invoke(level: Level) : GameSettings {
        return repo.getGameSettings(level)
    }
}