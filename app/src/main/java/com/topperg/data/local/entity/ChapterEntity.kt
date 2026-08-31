package com.topperg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class ChapterEntity(
    @PrimaryKey
    val id: String,
    val subjectId: String,
    val boardId: String,
    val classLevel: Int,
    val languageCode: String,
    val name: String,
    val nameLocal: String,
    val displayOrder: Int = 0,
    val isActive: Boolean = true,
    val contentVersion: Long = 0
)
