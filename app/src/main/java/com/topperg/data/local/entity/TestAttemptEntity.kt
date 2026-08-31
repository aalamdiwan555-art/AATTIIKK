package com.topperg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_attempts")
data class TestAttemptEntity(
    @PrimaryKey
    val id: String,
    val userId: String? = null,
    val type: String, // "mcq_practice", "chapter_test", "mock_test", "paper_test"
    val subjectId: String,
    val chapterId: String? = null,
    val boardId: String,
    val classLevel: Int,
    val languageCode: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val skippedAnswers: Int,
    val score: Float,
    val maxScore: Float,
    val timeTakenSeconds: Int,
    val durationSeconds: Int,
    val completedAt: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false,
    val questionDataJson: String = "{}" // JSON of question-answer mapping
)
