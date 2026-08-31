package com.topperg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topperg.data.local.entity.ProfileEntity
import com.topperg.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val profile: StateFlow<ProfileEntity?> = profileRepository.getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun updateBoard(boardId: String, boardName: String) {
        viewModelScope.launch {
            profileRepository.updateBoard(boardId, boardName)
        }
    }

    fun updateClass(classLevel: Int) {
        viewModelScope.launch {
            profileRepository.updateClass(classLevel)
        }
    }

    fun updateLanguage(languageCode: String, languageName: String) {
        viewModelScope.launch {
            profileRepository.updateLanguage(languageCode, languageName)
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            profileRepository.completeOnboarding()
        }
    }

    fun saveFullProfile(boardId: String, boardName: String, classLevel: Int, languageCode: String, languageName: String) {
        viewModelScope.launch {
            val profile = ProfileEntity(
                boardId = boardId,
                boardName = boardName,
                classLevel = classLevel,
                languageCode = languageCode,
                languageName = languageName,
                isOnboardingComplete = true,
                isGuest = true
            )
            profileRepository.saveProfile(profile)
        }
    }
}
