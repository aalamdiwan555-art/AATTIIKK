package com.topperg.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.topperg.data.local.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile WHERE id = 1 LIMIT 1")
    fun getProfile(): Flow<ProfileEntity?>

    @Query("SELECT * FROM profile WHERE id = 1 LIMIT 1")
    suspend fun getProfileSync(): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Update
    suspend fun updateProfile(profile: ProfileEntity)

    @Query("UPDATE profile SET isOnboardingComplete = :complete WHERE id = 1")
    suspend fun setOnboardingComplete(complete: Boolean)

    @Query("UPDATE profile SET boardId = :boardId, boardName = :boardName, updatedAt = :timestamp WHERE id = 1")
    suspend fun updateBoard(boardId: String, boardName: String, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE profile SET classLevel = :classLevel, updatedAt = :timestamp WHERE id = 1")
    suspend fun updateClass(classLevel: Int, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE profile SET languageCode = :languageCode, languageName = :languageName, updatedAt = :timestamp WHERE id = 1")
    suspend fun updateLanguage(languageCode: String, languageName: String, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE profile SET userId = :userId, isGuest = :isGuest, updatedAt = :timestamp WHERE id = 1")
    suspend fun updateUser(userId: String?, isGuest: Boolean, timestamp: Long = System.currentTimeMillis())
}
