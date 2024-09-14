package com.example.drowsiness.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drowsiness.data.models.Record
import com.example.drowsiness.data.repositories.DataStoreRepository
import com.example.drowsiness.data.repositories.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: RecordRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val date: MutableState<String> = mutableStateOf("")
    private val _allRecords = MutableStateFlow<List<Record>>(listOf<Record>())
    val allRecords: StateFlow<List<Record>> = _allRecords

    fun getAllRecords() {
        try {
            viewModelScope.launch {
                repository.getAllRecords.collect {
                    _allRecords.value = it
                }
            }
        } catch (e: Throwable) {
            _allRecords.value = listOf<Record>()
        }
    }

    fun addRecord(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val record = Record(
                date = date
            )
            repository.addRecord(record = record)
        }
    }

}