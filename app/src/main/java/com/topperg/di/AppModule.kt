package com.topperg.di

import android.content.Context
import androidx.room.Room
import com.topperg.data.local.TopperGDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TopperGDatabase {
        return Room.databaseBuilder(
            context,
            TopperGDatabase::class.java,
            "topperg_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBoardDao(database: TopperGDatabase) = database.boardDao()

    @Provides
    fun provideProfileDao(database: TopperGDatabase) = database.profileDao()

    @Provides
    fun provideSubjectDao(database: TopperGDatabase) = database.subjectDao()

    @Provides
    fun provideChapterDao(database: TopperGDatabase) = database.chapterDao()

    @Provides
    fun provideNoteDao(database: TopperGDatabase) = database.noteDao()

    @Provides
    fun provideMcqDao(database: TopperGDatabase) = database.mcqDao()

    @Provides
    fun provideTestAttemptDao(database: TopperGDatabase) = database.testAttemptDao()

    @Provides
    fun provideBookmarkDao(database: TopperGDatabase) = database.bookmarkDao()

    @Provides
    fun provideDownloadedContentDao(database: TopperGDatabase) = database.downloadedContentDao()

    @Provides
    fun providePreviousYearPaperDao(database: TopperGDatabase) = database.previousYearPaperDao()
}
