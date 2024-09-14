package com.example.drowsiness.di

import android.content.Context
import androidx.room.Room
import com.example.drowsiness.data.RecordDatabase
import com.example.drowsiness.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            RecordDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideDao(database: RecordDatabase) = database.recordDao()
}