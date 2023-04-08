package r.prokhorov.interactivestandardtask.presentation.chart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import r.prokhorov.interactivestandardtask.domain.Point
import r.prokhorov.interactivestandardtask.presentation.core.LandscapePreview
import r.prokhorov.interactivestandardtask.presentation.ui.theme.InteractiveStandardTaskTheme

@Composable
fun PointsChart(points: List<Point>, modifier: Modifier = Modifier) {
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

                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    axisRight.isEnabled = false

                    xAxis.textColor = cvTextColor
                    axisLeft.textColor = cvTextColor
                    description.textColor = cvTextColor
                    legend.textColor = cvTextColor
                }
            },
            update = { chart ->
                val entries = points.map { Entry(it.x, it.y) } // todo: up to 1000 points map in VM

                val dataSet = LineDataSet(entries, "Points data set")
                dataSet.valueTextColor = cvTextColor
                dataSet.color = lineColor
                dataSet.setCircleColor(lineColor)
                dataSet.setDrawCircleHole(false)

                chart.data = LineData(dataSet)
                chart.invalidate()
            },
            modifier = Modifier.fillMaxSize()
        )
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
                )
            )
        }
    }
}
