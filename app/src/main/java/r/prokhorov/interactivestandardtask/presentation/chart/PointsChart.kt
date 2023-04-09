package r.prokhorov.interactivestandardtask.presentation.chart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import r.prokhorov.interactivestandardtask.R
import r.prokhorov.interactivestandardtask.domain.Point
import r.prokhorov.interactivestandardtask.presentation.core.LandscapePreview
import r.prokhorov.interactivestandardtask.presentation.ui.theme.InteractiveStandardTaskTheme

@Composable
fun PointsChart(
    points: List<Point>,
    modifier: Modifier = Modifier,
    isSmoothed: Boolean = false,
    onModeClicked: () -> Unit
) {

    val cvTextColor = MaterialTheme.colors.onSurface.toArgb()
    val lineColor = MaterialTheme.colors.primary.toArgb()

    Box(modifier = modifier.fillMaxSize()) {
        // AndroidView not weighted in case of no Box wrapper :| D>-
        AndroidView(
            factory = {
                LineChart(it).apply {
                    setTouchEnabled(true)
                    isDragEnabled = true
                    isScaleXEnabled = true
                    isScaleYEnabled = true
                    description.isEnabled = false

                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    axisRight.isEnabled = false

                    xAxis.textColor = cvTextColor
                    axisLeft.textColor = cvTextColor

                    legend.textColor = cvTextColor
                }
            },
            update = { chart ->
                val entries = points.map { Entry(it.x, it.y) } // todo: up to 1000 points map in VM

                val dataSet = LineDataSet(entries, "Points data set")
                if (isSmoothed) {
                    dataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                } else {
                    dataSet.mode = LineDataSet.Mode.LINEAR
                }

                dataSet.valueTextColor = cvTextColor
                dataSet.color = lineColor
                dataSet.setCircleColor(lineColor)
                dataSet.setDrawCircleHole(false)

                chart.data = LineData(dataSet)
                chart.invalidate()
            },
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            // todo:
            Surface(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            ) {
                IconButton(onClick = onModeClicked) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_change_mode),
                        contentDescription = "Change mode"
                    )
                }
            }
        }
    }
}

@LandscapePreview
@Composable
fun PointsChartPreview() {
    InteractiveStandardTaskTheme {
        Surface {
            PointsChart(
                listOf(
                    Point(0.37f, 17.15f),
                    Point(5.73f, 41.56f),
                    Point(11.82f, 7.91f),
                    Point(42.51f, -14.33f),
                    Point(3.95f, -11.2f)
                ),
                onModeClicked = {}
            )
        }
    }
}
