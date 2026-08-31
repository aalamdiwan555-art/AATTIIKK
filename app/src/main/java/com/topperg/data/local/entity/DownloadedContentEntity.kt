package com.topperg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_content")
data class DownloadedContentEntity(
    @PrimaryKey
    val id: String,
    val contentType: String, // "subject", "chapter", "note", "mcq_set"
    val contentId: String,
    val title: String,
    val subjectId: String? = null,
    val boardId: String,
    val classLevel: Int,
    val languageCode: String,
    val fileSizeBytes: Long = 0,
    val downloadedAt: Long = System.currentTimeMillis(),
    val localPath: String = ""
)
