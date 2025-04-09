package com.metoly.localbottomsheet.presentation.test_bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestBottomSheetViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    private var autoIncrementJob: Job? = null

    init {
        startAutoIncrement()
    }

    fun startAutoIncrement() {
        autoIncrementJob?.cancel()

        autoIncrementJob = viewModelScope.launch {
            while(true) {
                delay(1000)
                _count.value = _count.value + 1
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        autoIncrementJob?.cancel()
    }
}