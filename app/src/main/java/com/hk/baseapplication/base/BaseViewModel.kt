package com.hk.baseapplication.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiAction : BaseUiAction, UiState : BaseUiState> : ViewModel() {

    private val _actionFlow = MutableSharedFlow<UiAction>()

    internal abstract val _state: MutableStateFlow<UiState>

    val state: StateFlow<UiState>
        get() = _state.asStateFlow()

    private var job: Job? = null

    init {
        _actionFlow.process().launchIn(viewModelScope)
    }

    protected fun doJob(function: () -> Int) {
        job?.cancel()
        job = viewModelScope.launch { function() }
    }

    protected abstract fun Flow<UiAction>.process(): Flow<UiAction>

    fun processAction(action: UiAction): Boolean {
        return _actionFlow.tryEmit(action)
    }

}