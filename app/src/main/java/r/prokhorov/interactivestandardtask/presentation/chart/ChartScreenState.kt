package r.prokhorov.interactivestandardtask.presentation.chart

import r.prokhorov.interactivestandardtask.domain.Point

data class ChartScreenState(
    val isLoading: Boolean = false,
    val data: List<Point> = emptyList(),
    val error: String = ""
)