package r.prokhorov.interactivestandardtask.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import r.prokhorov.interactivestandardtask.domain.GetPointsUseCase
import r.prokhorov.interactivestandardtask.domain.common.Result
import javax.inject.Inject

private const val INPUT_STATE_KEY = "input_state"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(MainScreenState()) // w/o saving in case of DKA
    val state: State<MainScreenState> = _state

    val inputState = savedStateHandle.getStateFlow(INPUT_STATE_KEY, "")

    fun updateCountInput(input: String) {
        if (input.isDigitsOnly()) {
            savedStateHandle[INPUT_STATE_KEY] = input
            _state.value = MainScreenState(isInputError = false)
        }
    }

    fun fetchPoints() {
        val input = inputState.value

        _state.value = MainScreenState(isInputError = !input.isValid())

        if (input.isValid()) {
            val count = input.toIntOrNull() ?: 0

            _state.value = MainScreenState(isLoading = true)
            getPointsUseCase(count).onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _state.value = MainScreenState(isPointsFetched = true)
                    }
                    is Result.Failure -> {
                        _state.value = MainScreenState(error = result.reason)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun String.isValid() = isDigitsOnly() && isNotBlank()
}