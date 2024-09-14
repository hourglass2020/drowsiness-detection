package com.example.drowsiness.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.drowsiness.data.models.Record

@Database(entities = [Record::class], version = 1, exportSchema = false)
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}