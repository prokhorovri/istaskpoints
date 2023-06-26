package r.prokhorov.interactivestandardtask.presentation.start

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import r.prokhorov.interactivestandardtask.domain.FetchPointsUseCase
import r.prokhorov.interactivestandardtask.domain.common.Result
import javax.inject.Inject

private const val INPUT_STATE_KEY = "input_state"

@HiltViewModel
class StartViewModel @Inject constructor(
    private val fetchPointsUseCase: FetchPointsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(StartScreenState()) // w/o saving in case of DKA
    val state: State<StartScreenState> = _state

    private val _isFetched = MutableSharedFlow<Boolean>()
    val isFetched = _isFetched.asSharedFlow()

    val inputState = savedStateHandle.getStateFlow(INPUT_STATE_KEY, "")

    fun updateCountInput(input: String) {
        if (input.isDigitsOnly()) {
            savedStateHandle[INPUT_STATE_KEY] = input
            _state.value = StartScreenState(isInputError = false)
        }
    }

    fun fetchPoints() {
        val input = inputState.value

        _state.value = StartScreenState(isInputError = !input.isValid())

        if (input.isValid()) {
            val count = input.toIntOrNull() ?: 0

            _state.value = StartScreenState(isLoading = true)
            viewModelScope.launch {
                when (val result = fetchPointsUseCase(count)) {
                    is Result.Success -> {
                        _state.value = StartScreenState()
                        _isFetched.emit(true)
                        Log.d("POINTS", "points = ${result.data}")
                    }
                    is Result.Failure -> {
                        // make reason more meaningful and display count error on the textfield
                        _state.value = StartScreenState(error = result.reason)
                    }
                }
            }
        }
    }

    private fun String.isValid() = isDigitsOnly() && isNotBlank()
}