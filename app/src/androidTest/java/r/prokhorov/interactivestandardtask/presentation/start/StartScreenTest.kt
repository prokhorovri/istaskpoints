package r.prokhorov.interactivestandardtask.presentation.start

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import r.prokhorov.interactivestandardtask.di.AppModule
import r.prokhorov.interactivestandardtask.presentation.MainActivity
import r.prokhorov.interactivestandardtask.presentation.ScreenRoute
import r.prokhorov.interactivestandardtask.presentation.ui.theme.InteractiveStandardTaskTheme

@HiltAndroidTest
@UninstallModules(AppModule::class)
class StartScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            InteractiveStandardTaskTheme {
                NavHost(
                    navController = navController,
                    startDestination = ScreenRoute.Start.route
                ) {
                    composable(route = ScreenRoute.Start.route) {
                        StartScreen(navController = navController)
                    }
                }
            }
        }
        mockWebServer.start()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun clickFetch_isErrorVisible() {

    }

}