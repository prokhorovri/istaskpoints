package r.prokhorov.interactivestandardtask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import r.prokhorov.interactivestandardtask.presentation.chart.ChartScreen
import r.prokhorov.interactivestandardtask.presentation.start.StartScreen
import r.prokhorov.interactivestandardtask.presentation.ui.theme.InteractiveStandardTaskTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InteractiveStandardTaskTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.Start.route
                    ) {
                        composable(ScreenRoute.Start.route) {
                            StartScreen(navController)
                        }
                        composable(ScreenRoute.Chart.route) {
                            ChartScreen()
                        }
                    }
                }
            }
        }
    }
}
