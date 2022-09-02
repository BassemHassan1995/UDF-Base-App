package com.hk.baseapplication.ui.splash

import com.hk.baseapplication.base.BaseUiState

sealed interface SplashState : BaseUiState{

    val count: Int

    data class InitialState(override val count: Int = 0) : SplashState

    data class UpdateState(override val count: Int) : SplashState

}
