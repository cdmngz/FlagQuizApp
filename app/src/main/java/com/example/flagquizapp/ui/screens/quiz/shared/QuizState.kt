package com.example.flagquizapp.ui.screens.quiz.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.flagquizapp.model.Country
import com.example.flagquizapp.model.QuizRound
import kotlinx.coroutines.delay

@Stable
class QuizState(
    val quizRounds: List<QuizRound>,
    val totalRounds: Int,
    val onQuizFinished: () -> Unit
) {
    var round by mutableStateOf(0)
    var answered by mutableStateOf(false)
    var isCorrect by mutableStateOf(false)
    var score by mutableIntStateOf(0)
    var clickedCountry by mutableStateOf<Country?>(null)

    /** null once you’ve gone past the last round */
    val currentRound: QuizRound?
        get() = quizRounds.getOrNull(round)

    /** flips true once round >= totalRounds */
    val finished: Boolean
        get() = round >= totalRounds

    fun onAnswerSelected(selected: Country) {
        val roundData = currentRound ?: return
        if (answered) return

        clickedCountry = selected
        isCorrect = (selected == roundData.correct)
        answered = true
        if (isCorrect) score++
    }

    /** advance to next round or mark finished */
    suspend fun advance() {
        delay(1000)
        if (round < totalRounds - 1) {
            // move to next
            round++
            resetRound()
        } else {
            // last round → bump past end so `finished == true`
            round = totalRounds
            onQuizFinished()
        }
    }

    private fun resetRound() {
        answered = false
        clickedCountry = null
        isCorrect = false
    }

    /** restart the whole quiz */
    fun restart() {
        round = 0
        score = 0
        answered = false
        clickedCountry = null
        isCorrect = false
    }
}

@Composable
fun rememberQuizState(
    countries: List<Country>,
    totalRounds: Int = 10,
    onQuizFinished: () -> Unit
): QuizState {
    val quizRounds = remember {
        buildQuizRounds(
            countries = countries,
            totalRounds = totalRounds
        )
    }
    return remember {
        QuizState(
            quizRounds = quizRounds,
            totalRounds = quizRounds.size,
            onQuizFinished = onQuizFinished
        )
    }
}
