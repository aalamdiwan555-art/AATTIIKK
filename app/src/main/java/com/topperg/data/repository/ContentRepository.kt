package com.topperg.data.repository

import com.topperg.data.local.dao.ChapterDao
import com.topperg.data.local.dao.McqDao
import com.topperg.data.local.dao.NoteDao
import com.topperg.data.local.dao.PreviousYearPaperDao
import com.topperg.data.local.dao.SubjectDao
import com.topperg.data.local.entity.ChapterEntity
import com.topperg.data.local.entity.McqEntity
import com.topperg.data.local.entity.NoteEntity
import com.topperg.data.local.entity.PreviousYearPaperEntity
import com.topperg.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepository @Inject constructor(
    private val subjectDao: SubjectDao,
    private val chapterDao: ChapterDao,
    private val noteDao: NoteDao,
    private val mcqDao: McqDao,
    private val paperDao: PreviousYearPaperDao
) {
    // Subjects
    fun getSubjects(boardId: String, classLevel: Int, languageCode: String): Flow<List<SubjectEntity>> =
        subjectDao.getSubjects(boardId, classLevel, languageCode)

    suspend fun getSubjectById(subjectId: String): SubjectEntity? =
        subjectDao.getSubjectById(subjectId)

    // Chapters
    fun getChapters(subjectId: String, boardId: String, classLevel: Int, languageCode: String): Flow<List<ChapterEntity>> =
        chapterDao.getChapters(subjectId, boardId, classLevel, languageCode)

    suspend fun getChapterById(chapterId: String): ChapterEntity? =
        chapterDao.getChapterById(chapterId)

    // Notes
    fun getNoteForChapter(chapterId: String): Flow<NoteEntity?> =
        noteDao.getNoteForChapter(chapterId)

    // MCQs
    fun getMcqsForChapter(chapterId: String, boardId: String, classLevel: Int, languageCode: String): Flow<List<McqEntity>> =
        mcqDao.getMcqsForChapter(chapterId, boardId, classLevel, languageCode)

    suspend fun getMcqsForSubject(subjectId: String, boardId: String, classLevel: Int, languageCode: String): List<McqEntity> =
        mcqDao.getMcqsForSubject(subjectId, boardId, classLevel, languageCode)

    // Papers
    fun getPapers(boardId: String, classLevel: Int, languageCode: String): Flow<List<PreviousYearPaperEntity>> =
        paperDao.getPapers(boardId, classLevel, languageCode)

    suspend fun getPaperById(paperId: String): PreviousYearPaperEntity? =
        paperDao.getPaperById(paperId)

    // Search
    suspend fun searchAll(query: String, boardId: String, classLevel: Int, languageCode: String): SearchResult {
        return SearchResult(
            notes = noteDao.searchNotes(query, boardId, classLevel, languageCode),
            mcqs = mcqDao.searchMcqs(query, boardId, classLevel, languageCode),
            papers = paperDao.searchPapers(query, boardId, classLevel, languageCode)
        )
    }
}

data class SearchResult(
    val notes: List<NoteEntity>,
    val mcqs: List<McqEntity>,
    val papers: List<PreviousYearPaperEntity>
)
