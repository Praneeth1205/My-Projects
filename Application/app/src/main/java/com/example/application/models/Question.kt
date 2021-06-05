package com.example.application.models

data class Question(
    var id :Int,
    var question: String,
    var option_A: String,
    var option_B: String,
    var option_C: String,
    var option_D: String,
    var answer: Int
)