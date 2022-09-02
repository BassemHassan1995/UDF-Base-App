package com.hk.baseapplication.ui.splash

import com.hk.baseapplication.base.BaseUiAction

sealed class SplashAction : BaseUiAction() {
    object IncrementCounterAction : SplashAction()
    object DecrementCounterAction: SplashAction()
}