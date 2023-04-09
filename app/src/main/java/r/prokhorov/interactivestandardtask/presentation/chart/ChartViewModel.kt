package r.prokhorov.interactivestandardtask.presentation.chart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import r.prokhorov.interactivestandardtask.domain.GetSortedPointsUseCase
import r.prokhorov.interactivestandardtask.domain.Point
import r.prokhorov.interactivestandardtask.domain.common.Result
import javax.inject.Inject

private const val SMOOTH_STATE_KEY = "smooth_state"

@HiltViewModel
class ChartViewModel @Inject constructor(
    getSortedPointsUseCase: GetSortedPointsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = mutableStateOf(ChartScreenState())
    val uiState: State<ChartScreenState> = _uiState

    val isSmoothed = savedStateHandle.getStateFlow(SMOOTH_STATE_KEY, false)

    init {
        getSortedPointsUseCase().onEach(::updateState).launchIn(viewModelScope)
    }

    fun switchSmoothMode() {
        savedStateHandle[SMOOTH_STATE_KEY] = !isSmoothed.value
    }

    private fun updateState(result: Result<List<Point>>) {
        when (result) {
            is Result.Success -> {
                _uiState.value = ChartScreenState(data = result.data)
            }
            is Result.Failure -> {
                _uiState.value = ChartScreenState(error = result.reason)
            }
        }
    }

}