package com.topperg.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.topperg.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Query("""
        SELECT * FROM subjects 
        WHERE boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode AND isActive = 1 
        ORDER BY displayOrder ASC
    """)
    fun getSubjects(boardId: String, classLevel: Int, languageCode: String): Flow<List<SubjectEntity>>

    @Query("""
        SELECT * FROM subjects 
        WHERE boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode AND isActive = 1 
        ORDER BY displayOrder ASC
    """)
    suspend fun getSubjectsSync(boardId: String, classLevel: Int, languageCode: String): List<SubjectEntity>

    @Query("SELECT * FROM subjects WHERE id = :subjectId LIMIT 1")
    suspend fun getSubjectById(subjectId: String): SubjectEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<SubjectEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity)

    @Query("DELETE FROM subjects WHERE boardId = :boardId AND classLevel = :classLevel AND languageCode = :languageCode")
    suspend fun deleteSubjectsForProfile(boardId: String, classLevel: Int, languageCode: String)
}
