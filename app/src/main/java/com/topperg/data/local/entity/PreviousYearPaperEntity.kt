package com.topperg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "previous_year_papers")
data class PreviousYearPaperEntity(
    @PrimaryKey
    val id: String,
    val boardId: String,
    val classLevel: Int,
    val subjectId: String,
    val languageCode: String,
    val year: Int,
    val title: String,
    val description: String? = null,
    val content: String, // JSON string of questions
    val isDownloaded: Boolean = false,
    val contentVersion: Long = 0
)
