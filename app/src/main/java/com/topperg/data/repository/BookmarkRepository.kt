package com.topperg.data.repository

import com.topperg.data.local.dao.BookmarkDao
import com.topperg.data.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookmarkRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {
    fun getAllBookmarks(): Flow<List<BookmarkEntity>> = bookmarkDao.getAllBookmarks()

    fun getBookmarksByType(type: String): Flow<List<BookmarkEntity>> =
        bookmarkDao.getBookmarksByType(type)

    fun isBookmarked(itemId: String): Flow<Boolean> = bookmarkDao.isBookmarked(itemId)

    suspend fun toggleBookmark(bookmark: BookmarkEntity) {
        val existing = bookmarkDao.getBookmarkByItemId(bookmark.itemId)
        if (existing != null) {
            bookmarkDao.deleteBookmark(bookmark.itemId)
        } else {
            bookmarkDao.insertBookmark(bookmark)
        }
    }

    suspend fun deleteBookmark(itemId: String) {
        bookmarkDao.deleteBookmark(itemId)
    }
}
