package com.hk.baseapplication.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiAction: BaseUiAction, UiState: BaseUiState> : ViewModel() {

    protected abstract val uiState: MutableStateFlow<UiState>

    val state: StateFlow<UiState> by lazy { uiState }

    private var job: Job? = null

    protected fun doJob(function: () -> Int) {
        job?.cancel()
        job = viewModelScope.launch { function() }
    }

    abstract fun processAction(uiAction: UiAction)

}