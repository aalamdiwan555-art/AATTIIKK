package com.topperg.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.topperg.data.local.entity.PreviousYearPaperEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PreviousYearPaperDao {
    @Query("""
        SELECT * FROM previous_year_papers 
        WHERE boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode 
        ORDER BY year DESC, title ASC
    """)
    fun getPapers(boardId: String, classLevel: Int, languageCode: String): Flow<List<PreviousYearPaperEntity>>

    @Query("""
        SELECT * FROM previous_year_papers 
        WHERE boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode 
        AND year = :year
        ORDER BY title ASC
    """)
    suspend fun getPapersByYear(boardId: String, classLevel: Int, languageCode: String, year: Int): List<PreviousYearPaperEntity>

    @Query("""
        SELECT * FROM previous_year_papers 
        WHERE boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode 
        AND subjectId = :subjectId
        ORDER BY year DESC
    """)
    suspend fun getPapersBySubject(boardId: String, classLevel: Int, languageCode: String, subjectId: String): List<PreviousYearPaperEntity>

    @Query("""
        SELECT * FROM previous_year_papers 
        WHERE boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode 
        AND (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%')
    """)
    suspend fun searchPapers(query: String, boardId: String, classLevel: Int, languageCode: String): List<PreviousYearPaperEntity>

    @Query("SELECT * FROM previous_year_papers WHERE id = :paperId LIMIT 1")
    suspend fun getPaperById(paperId: String): PreviousYearPaperEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPapers(papers: List<PreviousYearPaperEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaper(paper: PreviousYearPaperEntity)
}
