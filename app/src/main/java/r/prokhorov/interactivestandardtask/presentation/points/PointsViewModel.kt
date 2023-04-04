package r.prokhorov.interactivestandardtask.presentation.points

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import r.prokhorov.interactivestandardtask.domain.GetPointsUseCase
import r.prokhorov.interactivestandardtask.domain.common.Result
import javax.inject.Inject

@HiltViewModel
class PointsViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase
) : ViewModel() {

    // add count state

    fun fetchPoints() {
        getPointsUseCase(50).onEach { result ->
            when(result) {
                is Result.Success -> {
                    Log.d("REQUEST", "data: ${result.data}")
                }
                is Result.Failure -> {
                    Log.d("REQUEST", "loading failure")
                    Log.d("REQUEST", "message: ${result.reason}")
                }
            }
        }.launchIn(viewModelScope)
    }
}