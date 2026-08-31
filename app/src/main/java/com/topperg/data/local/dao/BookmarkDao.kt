package com.topperg.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.topperg.data.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY bookmarkedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks WHERE itemType = :type ORDER BY bookmarkedAt DESC")
    fun getBookmarksByType(type: String): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks WHERE itemId = :itemId LIMIT 1")
    suspend fun getBookmarkByItemId(itemId: String): BookmarkEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE itemId = :itemId LIMIT 1)")
    fun isBookmarked(itemId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE itemId = :itemId")
    suspend fun deleteBookmark(itemId: String)

    @Query("SELECT * FROM bookmarks WHERE isSynced = 0")
    suspend fun getUnsyncedBookmarks(): List<BookmarkEntity>

    @Query("UPDATE bookmarks SET isSynced = 1 WHERE id = :bookmarkId")
    suspend fun markAsSynced(bookmarkId: String)
}
