package com.topperg.data.repository

import com.topperg.data.local.dao.BoardDao
import com.topperg.data.local.entity.BoardEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoardRepository @Inject constructor(
    private val boardDao: BoardDao
) {
    fun getAllBoards(): Flow<List<BoardEntity>> = boardDao.getAllBoards()

    suspend fun getBoardById(boardId: String): BoardEntity? = boardDao.getBoardById(boardId)

    suspend fun seedBoards(boards: List<BoardEntity>) {
        if (boardDao.getBoardCount() == 0) {
            boardDao.insertBoards(boards)
        }
    }
}
