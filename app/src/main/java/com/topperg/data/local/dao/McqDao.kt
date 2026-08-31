package com.topperg.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.topperg.data.local.entity.McqEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface McqDao {
    @Query("""
        SELECT * FROM mcqs 
        WHERE chapterId = :chapterId AND boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode 
        ORDER BY displayOrder ASC
    """)
    fun getMcqsForChapter(chapterId: String, boardId: String, classLevel: Int, languageCode: String): Flow<List<McqEntity>>

    @Query("""
        SELECT * FROM mcqs 
        WHERE chapterId = :chapterId AND boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode 
        ORDER BY displayOrder ASC
    """)
    suspend fun getMcqsForChapterSync(chapterId: String, boardId: String, classLevel: Int, languageCode: String): List<McqEntity>

    @Query("""
        SELECT * FROM mcqs 
        WHERE subjectId = :subjectId AND boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode 
        ORDER BY displayOrder ASC
    """)
    suspend fun getMcqsForSubject(subjectId: String, boardId: String, classLevel: Int, languageCode: String): List<McqEntity>

    @Query("""
        SELECT * FROM mcqs 
        WHERE boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode 
        AND (question LIKE '%' || :query || '%' OR optionA LIKE '%' || :query || '%' OR optionB LIKE '%' || :query || '%'
        OR optionC LIKE '%' || :query || '%' OR optionD LIKE '%' || :query || '%')
    """)
    suspend fun searchMcqs(query: String, boardId: String, classLevel: Int, languageCode: String): List<McqEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMcqs(mcqs: List<McqEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMcq(mcq: McqEntity)
}
