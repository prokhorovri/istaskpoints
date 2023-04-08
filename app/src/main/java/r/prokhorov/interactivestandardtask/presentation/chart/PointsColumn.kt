package r.prokhorov.interactivestandardtask.presentation.chart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import r.prokhorov.interactivestandardtask.domain.Point

@Composable
fun PointsColumn(modifier: Modifier = Modifier, points: List<Point>) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(points) { point ->
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp),
                text = "point x = ${point.x}, y = ${point.y}"
            )
        }
    }
}
