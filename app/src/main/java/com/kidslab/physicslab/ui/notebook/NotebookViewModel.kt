package com.kidslab.physicslab.ui.notebook

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kidslab.physicslab.data.local.entity.QuizQuestionEntity
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotebookViewModel(private val repository: PhysicsLabRepository) : ViewModel() {

    private var userId: Long = 0L

    val badges = repository.observeAllBadges()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _earnedBadgeIds = MutableStateFlow<Set<String>>(emptySet())
    val earnedBadgeIds: StateFlow<Set<String>> = _earnedBadgeIds

    private val _completedCount = MutableStateFlow(0)
    val completedCount: StateFlow<Int> = _completedCount

    var quizQuestions by mutableStateOf<List<QuizQuestionEntity>>(emptyList())
        private set
    var currentQuizIndex by mutableStateOf(0)
        private set
    var quizScore by mutableStateOf(0)
        private set
    var quizSelectedOption by mutableStateOf<String?>(null)
        private set
    var quizFinished by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            userId = repository.getActiveProfile()?.id ?: 0L
            repository.observeUserBadges(userId).collect { list ->
                _earnedBadgeIds.value = list.map { it.badgeId }.toSet()
            }
        }
        viewModelScope.launch {
            userId = repository.getActiveProfile()?.id ?: userId
            repository.observeCompletedExperimentCount(userId).collect { _completedCount.value = it }
        }
        loadQuiz()
    }

    private fun loadQuiz() {
        viewModelScope.launch {
            quizQuestions = repository.getShuffledQuizQuestions().take(10)
        }
    }

    fun selectQuizOption(option: String) {
        if (quizSelectedOption != null) return
        quizSelectedOption = option
        val question = quizQuestions.getOrNull(currentQuizIndex) ?: return
        val correct = option == question.correctOption
        if (correct) quizScore++
        viewModelScope.launch {
            repository.saveQuizAttempt(question.id, userId, option, correct)
        }
    }

    fun nextQuizQuestion() {
        if (currentQuizIndex < quizQuestions.size - 1) {
            currentQuizIndex++
            quizSelectedOption = null
        } else {
            quizFinished = true
        }
    }

    fun restartQuiz() {
        currentQuizIndex = 0
        quizScore = 0
        quizSelectedOption = null
        quizFinished = false
        loadQuiz()
    }

    class Factory(private val repository: PhysicsLabRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = NotebookViewModel(repository) as T
    }
}
