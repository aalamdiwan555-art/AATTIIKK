package com.topperg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mcqs")
data class McqEntity(
    @PrimaryKey
    val id: String,
    val chapterId: String,
    val subjectId: String,
    val boardId: String,
    val classLevel: Int,
    val languageCode: String,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: Int, // 1=A, 2=B, 3=C, 4=D
    val explanation: String,
    val difficulty: String = "medium", // easy, medium, hard
    val displayOrder: Int = 0,
    val isDownloaded: Boolean = false
)
