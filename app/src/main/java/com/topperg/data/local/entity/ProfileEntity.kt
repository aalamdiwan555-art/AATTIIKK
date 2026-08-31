package com.topperg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey
    val id: Int = 1,
    val boardId: String = "",
    val boardName: String = "",
    val classLevel: Int = 1,
    val languageCode: String = "en",
    val languageName: String = "English",
    val isOnboardingComplete: Boolean = false,
    val userId: String? = null, // Retained for existing local database compatibility
    val isGuest: Boolean = true, // Retained for existing local database compatibility
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
