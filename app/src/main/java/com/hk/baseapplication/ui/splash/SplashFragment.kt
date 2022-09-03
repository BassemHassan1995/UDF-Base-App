package com.hk.baseapplication.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hk.baseapplication.base.BaseFragment
import com.hk.baseapplication.databinding.FragmentSplashBinding
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashAction, SplashState>() {

    override val viewModel by activityViewModels<SplashViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    it.handleUiState()
                }
            }
        }
    }

    override fun setupViews() {
        binding.btnDecrement.setOnClickListener {
            lifecycleScope.launch {
                actionChannel.send(SplashAction.DecrementCounterAction)
            }
        }

        binding.btnIncrement.setOnClickListener {
            lifecycleScope.launch {
                actionChannel.send(SplashAction.IncrementCounterAction)
            }
        }
    }

    override fun SplashState.handleUiState() {
        binding.tvCount.text = count.toString()
    }
}