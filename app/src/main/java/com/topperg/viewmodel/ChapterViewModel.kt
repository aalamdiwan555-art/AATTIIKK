package com.topperg.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topperg.data.local.entity.ChapterEntity
import com.topperg.data.repository.ContentRepository
import com.topperg.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ChapterViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val profileRepository: ProfileRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val subjectId: String = savedStateHandle["subjectId"] ?: ""

    val chapters: StateFlow<List<ChapterEntity>> = profileRepository.getProfile()
        .flatMapLatest { profile ->
            if (profile != null && profile.isOnboardingComplete && subjectId.isNotEmpty()) {
                contentRepository.getChapters(subjectId, profile.boardId, profile.classLevel, profile.languageCode)
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
