package com.topperg.data.repository

import com.topperg.data.local.dao.TestAttemptDao
import com.topperg.data.local.entity.TestAttemptEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestRepository @Inject constructor(
    private val testAttemptDao: TestAttemptDao
) {
    fun getAllAttempts(): Flow<List<TestAttemptEntity>> = testAttemptDao.getAllAttempts()

    fun getAttemptsForSubject(subjectId: String): Flow<List<TestAttemptEntity>> =
        testAttemptDao.getAttemptsForSubject(subjectId)

    suspend fun saveAttempt(attempt: TestAttemptEntity) {
        testAttemptDao.insertAttempt(attempt)
    }

    suspend fun getUnsyncedAttempts(): List<TestAttemptEntity> =
        testAttemptDao.getUnsyncedAttempts()

    suspend fun markAsSynced(attemptId: String) {
        testAttemptDao.markAsSynced(attemptId)
    }
}
