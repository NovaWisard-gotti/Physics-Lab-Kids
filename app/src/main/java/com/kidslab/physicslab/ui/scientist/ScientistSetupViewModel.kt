package com.kidslab.physicslab.ui.scientist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ScientistSetupUiState(
    val name: String = "",
    val avatarId: String = AVATARS.first(),
    val coatColorHex: String = COAT_COLORS.first(),
    val saved: Boolean = false
)

val AVATARS = listOf("🧑\u200D\uD83D\uDD2C", "\uD83D\uDC69\u200D\uD83D\uDD2C", "\uD83E\uDDD1\u200D\uD83D\uDD2C", "\uD83D\uDC68\u200D\uD83D\uDD2C")
val COAT_COLORS = listOf("#2F6FED", "#FF8A3D", "#33C481", "#8C6BFF", "#FF5C5C")

class ScientistSetupViewModel(private val repository: PhysicsLabRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ScientistSetupUiState())
    val uiState: StateFlow<ScientistSetupUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onAvatarChange(avatarId: String) {
        _uiState.value = _uiState.value.copy(avatarId = avatarId)
    }

    fun onCoatColorChange(colorHex: String) {
        _uiState.value = _uiState.value.copy(coatColorHex = colorHex)
    }

    fun save() {
        val state = _uiState.value
        if (state.name.isBlank()) return
        viewModelScope.launch {
            repository.createOrUpdateProfile(state.name.trim(), state.avatarId, state.coatColorHex)
            _uiState.value = state.copy(saved = true)
        }
    }

    class Factory(private val repository: PhysicsLabRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ScientistSetupViewModel(repository) as T
    }
}
