package com.topperg.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.topperg.data.local.entity.ChapterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Query("""
        SELECT * FROM chapters 
        WHERE subjectId = :subjectId AND boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode AND isActive = 1 
        ORDER BY displayOrder ASC
    """)
    fun getChapters(subjectId: String, boardId: String, classLevel: Int, languageCode: String): Flow<List<ChapterEntity>>

    @Query("SELECT * FROM chapters WHERE id = :chapterId LIMIT 1")
    suspend fun getChapterById(chapterId: String): ChapterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapter: ChapterEntity)
}
