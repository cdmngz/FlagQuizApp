package com.example.flagquizapp.model

enum class QuizType(val index: Int) {
    FLAGS(0),
    MAPS(1),
    MIXED(2),
    TIMED(3);

    companion object {
        fun from(index: Int): QuizType {
            return entries.firstOrNull { it.index == index } ?: FLAGS
        }
    }
}