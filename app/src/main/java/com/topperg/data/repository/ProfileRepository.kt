package com.topperg.data.repository

import com.topperg.data.local.dao.ProfileDao
import com.topperg.data.local.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {
    fun getProfile(): Flow<ProfileEntity?> = profileDao.getProfile()

    suspend fun getProfileSync(): ProfileEntity? = profileDao.getProfileSync()

    suspend fun saveProfile(profile: ProfileEntity) {
        profileDao.insertProfile(profile)
    }

    suspend fun updateBoard(boardId: String, boardName: String) {
        profileDao.updateBoard(boardId, boardName)
    }

    suspend fun updateClass(classLevel: Int) {
        profileDao.updateClass(classLevel)
    }

    suspend fun updateLanguage(languageCode: String, languageName: String) {
        profileDao.updateLanguage(languageCode, languageName)
    }

    suspend fun completeOnboarding() {
        profileDao.setOnboardingComplete(true)
    }
}
