package com.hk.baseapplication.ui.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.hk.baseapplication.base.BaseFragment
import com.hk.baseapplication.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashAction, SplashState>() {

    override val viewModel by activityViewModels<SplashViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)

    override fun setupViews() {
        binding.btnDecrement.setOnClickListener {
            viewModel.processAction(SplashAction.DecrementCounterAction)
        }

        binding.btnIncrement.setOnClickListener {
            viewModel.processAction(SplashAction.IncrementCounterAction)
        }
    }

    override fun SplashState.handleUiState() {
        binding.tvCount.text = count.toString()
    }
}