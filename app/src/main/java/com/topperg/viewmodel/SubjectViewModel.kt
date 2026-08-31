package com.topperg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topperg.data.local.entity.SubjectEntity
import com.topperg.data.repository.ContentRepository
import com.topperg.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val subjects: StateFlow<List<SubjectEntity>> = profileRepository.getProfile()
        .flatMapLatest { profile ->
            if (profile != null && profile.isOnboardingComplete) {
                contentRepository.getSubjects(profile.boardId, profile.classLevel, profile.languageCode)
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
