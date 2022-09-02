package com.hk.baseapplication.ui.splash

import com.hk.baseapplication.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class SplashViewModel : BaseViewModel<SplashAction, SplashState>() {

    override val _state: MutableStateFlow<SplashState>
        get() = MutableStateFlow(SplashState.InitialState())

    override fun Flow<SplashAction>.process(): Flow<SplashAction> = onEach {
        when (it) {
            SplashAction.DecrementCounterAction -> onDecrementCounter()
            SplashAction.IncrementCounterAction -> onIncrementCounter()
        }
    }

    private fun onDecrementCounter() {
        _state.update {
            SplashState.UpdateState(count = it.count - 1)
        }
    }

    private fun onIncrementCounter() {
        _state.update {
            SplashState.UpdateState(count = it.count + 1)
        }
    }
}