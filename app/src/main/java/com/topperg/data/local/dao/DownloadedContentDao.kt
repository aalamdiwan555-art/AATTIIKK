package com.topperg.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.topperg.data.local.entity.DownloadedContentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedContentDao {
    @Query("SELECT * FROM downloaded_content ORDER BY downloadedAt DESC")
    fun getAllDownloads(): Flow<List<DownloadedContentEntity>>

    @Query("SELECT * FROM downloaded_content WHERE contentId = :contentId LIMIT 1")
    suspend fun getDownload(contentId: String): DownloadedContentEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM downloaded_content WHERE contentId = :contentId LIMIT 1)")
    fun isDownloaded(contentId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownload(download: DownloadedContentEntity)

    @Query("DELETE FROM downloaded_content WHERE contentId = :contentId")
    suspend fun deleteDownload(contentId: String)

    @Query("SELECT SUM(fileSizeBytes) FROM downloaded_content")
    suspend fun getTotalStorageUsed(): Long?
}
