package com.example.drowsiness.data.repositories

import com.example.drowsiness.data.RecordDao
import com.example.drowsiness.data.models.Record
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordRepository @Inject constructor(private val recordDao: RecordDao) {
    val getAllRecords: Flow<List<Record>> = recordDao.getAllRecords()

    suspend fun addRecord(record: Record) {
        recordDao.addRecord(record = record)
    }
}