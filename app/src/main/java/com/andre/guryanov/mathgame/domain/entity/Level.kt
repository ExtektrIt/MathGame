package com.andre.guryanov.mathgame.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable {
    TEST, EASY, MEDIUM, HARD
}