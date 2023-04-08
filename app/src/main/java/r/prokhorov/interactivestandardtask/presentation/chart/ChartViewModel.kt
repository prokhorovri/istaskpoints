package r.prokhorov.interactivestandardtask.presentation.chart

import dagger.hilt.android.lifecycle.HiltViewModel
import r.prokhorov.interactivestandardtask.domain.GetSortedPointsUseCase
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val getSortedPointsUseCase: GetSortedPointsUseCase
) {

}