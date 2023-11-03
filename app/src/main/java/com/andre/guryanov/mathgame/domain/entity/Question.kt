package com.andre.guryanov.mathgame.domain.entity

data class Question (
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>,
        )