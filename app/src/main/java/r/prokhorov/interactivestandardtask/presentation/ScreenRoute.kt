package r.prokhorov.interactivestandardtask.presentation

sealed class ScreenRoute(val route: String) {
    object Start : ScreenRoute("start_screen")
    object Chart : ScreenRoute("chart_screen")
}