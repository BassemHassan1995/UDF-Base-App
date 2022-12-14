package com.hk.baseapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseFragment<Binding : ViewBinding, UiAction : BaseUiAction, UiState : BaseUiState> :
    Fragment() {

    protected abstract val viewModel: BaseViewModel<UiAction, UiState>

    private val actionChannel: Channel<UiAction> = Channel()

    private fun actions(): Flow<UiAction> = actionChannel.consumeAsFlow()

    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    protected abstract fun UiState.handleUiState()

    protected abstract fun setupViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actions().onEach(viewModel::processAction).launchIn(lifecycleScope)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    it.handleUiState()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)

        setupViews()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun performAction(action: UiAction) {
        lifecycleScope.launch {
            actionChannel.send(action)
        }
    }
}