package com.example.drowsiness.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.drowsiness.utils.Constants
import java.util.Date

@Entity(tableName = Constants.DATABASE_TABLE)
data class Record (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String
)