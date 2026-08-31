package com.topperg.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.topperg.data.local.dao.BoardDao
import com.topperg.data.local.dao.BookmarkDao
import com.topperg.data.local.dao.ChapterDao
import com.topperg.data.local.dao.DownloadedContentDao
import com.topperg.data.local.dao.McqDao
import com.topperg.data.local.dao.NoteDao
import com.topperg.data.local.dao.PreviousYearPaperDao
import com.topperg.data.local.dao.ProfileDao
import com.topperg.data.local.dao.SubjectDao
import com.topperg.data.local.dao.TestAttemptDao
import com.topperg.data.local.entity.BoardEntity
import com.topperg.data.local.entity.BookmarkEntity
import com.topperg.data.local.entity.ChapterEntity
import com.topperg.data.local.entity.DownloadedContentEntity
import com.topperg.data.local.entity.McqEntity
import com.topperg.data.local.entity.NoteEntity
import com.topperg.data.local.entity.PreviousYearPaperEntity
import com.topperg.data.local.entity.ProfileEntity
import com.topperg.data.local.entity.SubjectEntity
import com.topperg.data.local.entity.TestAttemptEntity

@Database(
    entities = [
        BoardEntity::class,
        ProfileEntity::class,
        SubjectEntity::class,
        ChapterEntity::class,
        NoteEntity::class,
        McqEntity::class,
        TestAttemptEntity::class,
        BookmarkEntity::class,
        DownloadedContentEntity::class,
        PreviousYearPaperEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TopperGDatabase : RoomDatabase() {
    abstract fun boardDao(): BoardDao
    abstract fun profileDao(): ProfileDao
    abstract fun subjectDao(): SubjectDao
    abstract fun chapterDao(): ChapterDao
    abstract fun noteDao(): NoteDao
    abstract fun mcqDao(): McqDao
    abstract fun testAttemptDao(): TestAttemptDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun downloadedContentDao(): DownloadedContentDao
    abstract fun previousYearPaperDao(): PreviousYearPaperDao
}
