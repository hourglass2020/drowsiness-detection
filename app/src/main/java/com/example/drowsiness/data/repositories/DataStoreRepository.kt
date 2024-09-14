package com.example.drowsiness.data.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {
}