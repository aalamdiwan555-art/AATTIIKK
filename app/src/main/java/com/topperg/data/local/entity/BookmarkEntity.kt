package com.topperg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey
    val id: String,
    val userId: String? = null,
    val itemType: String, // "note", "mcq_set", "paper"
    val itemId: String,
    val title: String,
    val subjectName: String? = null,
    val chapterName: String? = null,
    val bookmarkedAt: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false
)
