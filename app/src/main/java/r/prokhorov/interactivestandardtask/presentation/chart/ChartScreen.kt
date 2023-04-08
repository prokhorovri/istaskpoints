package r.prokhorov.interactivestandardtask.presentation.chart

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import r.prokhorov.interactivestandardtask.domain.Point
import r.prokhorov.interactivestandardtask.presentation.core.LandscapePreview
import r.prokhorov.interactivestandardtask.presentation.core.PortraitPreview
import r.prokhorov.interactivestandardtask.presentation.ui.theme.InteractiveStandardTaskTheme

@Composable
fun ChartScreen(viewModel: ChartViewModel = hiltViewModel()) {
    val state by viewModel.uiState
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            state.error.isNotBlank() -> Text(text = state.error, color = MaterialTheme.colors.error)
            else -> ChartLayout(state.data, isLandscape = maxWidth > maxHeight)
        }
    }
}

@Composable
fun ChartLayout(points: List<Point>, isLandscape: Boolean = false) {
    if (isLandscape) {
        Row(modifier = Modifier.fillMaxSize()) {
            PointsColumn(modifier = Modifier.weight(1f), points = points)
            PointsChart(modifier = Modifier.weight(2f), points = points)
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            PointsColumn(modifier = Modifier.weight(1f), points = points)
            PointsChart(modifier = Modifier.weight(2f), points = points)
        }
    }
}

@PortraitPreview
@Composable
fun ChartScreenPreview() {
    InteractiveStandardTaskTheme {
        Surface {
            ChartLayout(
                listOf(
                    Point(0.37f, 17.15f),
                    Point(5.73f, 41.56f),
                    Point(11.82f, 7.91f),
                    Point(42.51f, -14.33f),
                    Point(3.95f, -11.2f)
                )
            )
        }
    }
}

@LandscapePreview
@Composable
fun ChartScreenLandscapePreview() {
    InteractiveStandardTaskTheme {
        Surface {
            ChartLayout(
                listOf(
                    Point(0.37f, 17.15f),
                    Point(5.73f, 41.56f),
                    Point(11.82f, 7.91f),
                    Point(42.51f, -14.33f),
                    Point(3.95f, -11.2f)
                ),
                isLandscape = true
            )
        }
    }
}
