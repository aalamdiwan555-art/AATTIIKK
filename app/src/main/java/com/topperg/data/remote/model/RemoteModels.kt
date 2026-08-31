package com.topperg.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteBoard(
    val id: String,
    val name: String,
    @SerialName("full_name") val fullName: String,
    val type: String,
    val state: String? = null,
    @SerialName("display_order") val displayOrder: Int = 0,
    @SerialName("is_active") val isActive: Boolean = true
)

@Serializable
data class RemoteSubject(
    val id: String,
    @SerialName("board_id") val boardId: String,
    @SerialName("class_level") val classLevel: Int,
    @SerialName("language_code") val languageCode: String,
    val name: String,
    @SerialName("name_local") val nameLocal: String,
    @SerialName("icon_name") val iconName: String,
    @SerialName("color_hex") val colorHex: String,
    @SerialName("display_order") val displayOrder: Int = 0,
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("content_version") val contentVersion: Long = 0
)

@Serializable
data class RemoteChapter(
    val id: String,
    @SerialName("subject_id") val subjectId: String,
    @SerialName("board_id") val boardId: String,
    @SerialName("class_level") val classLevel: Int,
    @SerialName("language_code") val languageCode: String,
    val name: String,
    @SerialName("name_local") val nameLocal: String,
    @SerialName("display_order") val displayOrder: Int = 0,
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("content_version") val contentVersion: Long = 0
)

@Serializable
data class RemoteNote(
    val id: String,
    @SerialName("chapter_id") val chapterId: String,
    @SerialName("subject_id") val subjectId: String,
    @SerialName("board_id") val boardId: String,
    @SerialName("class_level") val classLevel: Int,
    @SerialName("language_code") val languageCode: String,
    val title: String,
    val content: String,
    @SerialName("content_version") val contentVersion: Long = 0
)

@Serializable
data class RemoteMcq(
    val id: String,
    @SerialName("chapter_id") val chapterId: String,
    @SerialName("subject_id") val subjectId: String,
    @SerialName("board_id") val boardId: String,
    @SerialName("class_level") val classLevel: Int,
    @SerialName("language_code") val languageCode: String,
    val question: String,
    @SerialName("option_a") val optionA: String,
    @SerialName("option_b") val optionB: String,
    @SerialName("option_c") val optionC: String,
    @SerialName("option_d") val optionD: String,
    @SerialName("correct_option") val correctOption: Int,
    val explanation: String,
    val difficulty: String = "medium",
    @SerialName("display_order") val displayOrder: Int = 0
)

@Serializable
data class RemotePaper(
    val id: String,
    @SerialName("board_id") val boardId: String,
    @SerialName("class_level") val classLevel: Int,
    @SerialName("subject_id") val subjectId: String,
    @SerialName("language_code") val languageCode: String,
    val year: Int,
    val title: String,
    val description: String? = null,
    val content: String,
    @SerialName("content_version") val contentVersion: Long = 0
)

@Serializable
data class ContentVersionResponse(
    @SerialName("board_id") val boardId: String,
    @SerialName("class_level") val classLevel: Int,
    @SerialName("language_code") val languageCode: String,
    @SerialName("subject_version") val subjectVersion: Long = 0,
    @SerialName("chapter_version") val chapterVersion: Long = 0,
    @SerialName("note_version") val noteVersion: Long = 0,
    @SerialName("mcq_version") val mcqVersion: Long = 0,
    @SerialName("paper_version") val paperVersion: Long = 0
)
