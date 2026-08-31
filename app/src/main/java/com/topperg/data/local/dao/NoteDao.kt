package com.topperg.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.topperg.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE chapterId = :chapterId LIMIT 1")
    fun getNoteForChapter(chapterId: String): Flow<NoteEntity?>

    @Query("SELECT * FROM notes WHERE chapterId = :chapterId LIMIT 1")
    suspend fun getNoteForChapterSync(chapterId: String): NoteEntity?

    @Query("""
        SELECT * FROM notes 
        WHERE boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode 
        AND (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%')
    """)
    suspend fun searchNotes(query: String, boardId: String, classLevel: Int, languageCode: String): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<NoteEntity>)

    @Query("UPDATE notes SET isDownloaded = 1, downloadedAt = :timestamp WHERE id = :noteId")
    suspend fun markAsDownloaded(noteId: String, timestamp: Long = System.currentTimeMillis())
}
