package com.topperg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey
    val id: String,
    val chapterId: String,
    val subjectId: String,
    val boardId: String,
    val classLevel: Int,
    val languageCode: String,
    val title: String,
    val content: String,
    val contentVersion: Long = 0,
    val isDownloaded: Boolean = false,
    val downloadedAt: Long? = null
)
