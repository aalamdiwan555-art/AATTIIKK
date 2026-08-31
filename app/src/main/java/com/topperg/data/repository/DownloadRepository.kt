package com.topperg.data.repository

import com.topperg.data.local.dao.DownloadedContentDao
import com.topperg.data.local.entity.DownloadedContentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadRepository @Inject constructor(
    private val downloadDao: DownloadedContentDao
) {
    fun getAllDownloads(): Flow<List<DownloadedContentEntity>> = downloadDao.getAllDownloads()

    fun isDownloaded(contentId: String): Flow<Boolean> = downloadDao.isDownloaded(contentId)

    suspend fun addDownload(download: DownloadedContentEntity) {
        downloadDao.insertDownload(download)
    }

    suspend fun removeDownload(contentId: String) {
        downloadDao.deleteDownload(contentId)
    }

    suspend fun getTotalStorageUsed(): Long {
        return downloadDao.getTotalStorageUsed() ?: 0L
    }
}
