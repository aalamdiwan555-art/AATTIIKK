package com.topperg.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.topperg.data.local.entity.TestAttemptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TestAttemptDao {
    @Query("SELECT * FROM test_attempts ORDER BY completedAt DESC")
    fun getAllAttempts(): Flow<List<TestAttemptEntity>>

    @Query("SELECT * FROM test_attempts WHERE subjectId = :subjectId ORDER BY completedAt DESC")
    fun getAttemptsForSubject(subjectId: String): Flow<List<TestAttemptEntity>>

    @Query("SELECT * FROM test_attempts WHERE type = :type ORDER BY completedAt DESC")
    fun getAttemptsByType(type: String): Flow<List<TestAttemptEntity>>

    @Query("SELECT * FROM test_attempts WHERE isSynced = 0")
    suspend fun getUnsyncedAttempts(): List<TestAttemptEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: TestAttemptEntity)

    @Query("UPDATE test_attempts SET isSynced = 1 WHERE id = :attemptId")
    suspend fun markAsSynced(attemptId: String)

    @Query("DELETE FROM test_attempts WHERE id = :attemptId")
    suspend fun deleteAttempt(attemptId: String)
}
