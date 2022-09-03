package com.hk.baseapplication.ui.splash

import com.hk.baseapplication.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SplashViewModel : BaseViewModel<SplashAction, SplashState>() {

    override val uiState: MutableStateFlow<SplashState> =
        MutableStateFlow(SplashState.InitialState())

    override fun processAction(uiAction: SplashAction) = when (uiAction) {
        SplashAction.DecrementCounterAction -> onDecrementCounter()
        SplashAction.IncrementCounterAction -> onIncrementCounter()
    }

    private fun onDecrementCounter() {
        uiState.update {
            SplashState.UpdateState(it.count - 1)
        }
    }

    private fun onIncrementCounter() {
        uiState.update {
            SplashState.UpdateState(count = it.count + 1)
        }
    }
}