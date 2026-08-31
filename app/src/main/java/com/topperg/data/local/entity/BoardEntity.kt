package com.topperg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boards")
data class BoardEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val fullName: String,
    val type: String, // "National", "National_Open", or "State"
    val state: String? = null,
    val displayOrder: Int = 0,
    val isActive: Boolean = true
)
