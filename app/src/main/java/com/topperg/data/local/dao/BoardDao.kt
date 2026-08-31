package com.topperg.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.topperg.data.local.entity.BoardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardDao {
    @Query("SELECT * FROM boards WHERE isActive = 1 ORDER BY displayOrder ASC, name ASC")
    fun getAllBoards(): Flow<List<BoardEntity>>

    @Query("SELECT * FROM boards WHERE isActive = 1 ORDER BY displayOrder ASC, name ASC")
    suspend fun getAllBoardsSync(): List<BoardEntity>

    @Query("SELECT * FROM boards WHERE id = :boardId LIMIT 1")
    suspend fun getBoardById(boardId: String): BoardEntity?

    @Query("SELECT * FROM boards WHERE type = :type AND isActive = 1 ORDER BY displayOrder ASC")
    fun getBoardsByType(type: String): Flow<List<BoardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoards(boards: List<BoardEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoard(board: BoardEntity)

    @Query("DELETE FROM boards")
    suspend fun deleteAllBoards()

    @Query("SELECT COUNT(*) FROM boards")
    suspend fun getBoardCount(): Int
}
