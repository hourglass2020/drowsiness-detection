package com.example.drowsiness.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.drowsiness.data.models.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Query("SELECT * FROM drowsy_table ORDER BY id ASC")
    fun getAllRecords(): Flow<List<Record>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecord(record: Record)
}