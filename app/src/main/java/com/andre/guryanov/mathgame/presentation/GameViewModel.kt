package com.andre.guryanov.mathgame.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andre.guryanov.mathgame.R
import com.andre.guryanov.mathgame.data.GameRepositoryImpl
import com.andre.guryanov.mathgame.domain.entity.GameResult
import com.andre.guryanov.mathgame.domain.entity.GameSettings
import com.andre.guryanov.mathgame.domain.entity.Level
import com.andre.guryanov.mathgame.domain.entity.Question
import com.andre.guryanov.mathgame.domain.useCases.GenerateQuestionsUseCase
import com.andre.guryanov.mathgame.domain.useCases.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level
) : ViewModel() {

    private lateinit var settings: GameSettings
    private var timer: CountDownTimer? = null

    private val repository = GameRepositoryImpl

    private val generateQuestionsUseCase = GenerateQuestionsUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private var answersCount = 0
    private var rightAnswersCount = 0


    init {
        startGame()
    }


    override fun onCleared() {
        super.onCleared()

        timer?.cancel()
    }

    fun selectAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateNewQuestion()
    }



    private fun startGame() {
        getGameSettings()
        startTimer()
        generateNewQuestion()
        updateProgress()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            application.getString(R.string.f_game__progress),
            rightAnswersCount,
            settings.minCountOfRightAnswers
        )
        _enoughCount.value = rightAnswersCount >= settings.minCountOfRightAnswers
        _enoughPercent.value = percent >= settings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (rightAnswersCount == 0) return 0
        return rightAnswersCount * 100 / answersCount
    }

    private fun checkAnswer(number: Int) {
        answersCount++
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) rightAnswersCount++
    }

    private fun getGameSettings() {
        settings = getGameSettingsUseCase(level)
        _minPercent.value = settings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            settings.gameTimeInSeconds * MILLIS_IN_SECOND,
            MILLIS_IN_SECOND
        ) {
            override fun onTick(p0: Long) {
                _formattedTime.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    private fun generateNewQuestion() {
        _question.value = generateQuestionsUseCase(settings.maxSumValue)
    }

    private fun formatTime(timeMillis: Long): String {
        val min = timeMillis / (60 * MILLIS_IN_SECOND)
        val sec = timeMillis % (60 * MILLIS_IN_SECOND) / MILLIS_IN_SECOND
        return String.format("%02d:%02d", min, sec)
//        return timeMillis.toString()
    }

    private fun finishGame() {

        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            rightAnswersCount,
            answersCount,
            settings

        )

    }

    companion object {

        private const val MILLIS_IN_SECOND = 1_000L
    }

}