package com.topperg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey
    val id: String,
    val boardId: String,
    val classLevel: Int,
    val languageCode: String,
    val name: String,
    val nameLocal: String,
    val iconName: String,
    val colorHex: String,
    val displayOrder: Int = 0,
    val isActive: Boolean = true,
    val contentVersion: Long = 0
)
