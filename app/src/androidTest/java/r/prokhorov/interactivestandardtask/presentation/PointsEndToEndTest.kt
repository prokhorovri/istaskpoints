package r.prokhorov.interactivestandardtask.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import r.prokhorov.interactivestandardtask.di.AppModule
import r.prokhorov.interactivestandardtask.presentation.chart.ChartScreen
import r.prokhorov.interactivestandardtask.presentation.core.util.TestTags
import r.prokhorov.interactivestandardtask.presentation.start.StartScreen
import r.prokhorov.interactivestandardtask.presentation.ui.theme.InteractiveStandardTaskTheme
import java.net.HttpURLConnection

@OptIn(ExperimentalTestApi::class)
@HiltAndroidTest
@UninstallModules(AppModule::class)
class PointsEndToEndTest {

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
                    composable(ScreenRoute.Start.route) {
                        StartScreen(navController)
                    }
                    composable(ScreenRoute.Chart.route) {
                        ChartScreen()
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
    fun fetchPointsFromServer_displayChartAfterwards() {
        val inputStream = InstrumentationRegistry.getInstrumentation().context.assets.open("points.json")
        val buffer = Buffer().apply { writeAll(inputStream.source()) }

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(buffer)
        )

        // it doesn't matter how many points I put here, points.json has only 10
        composeRule.onNodeWithTag(TestTags.INPUT_FIELD).performTextInput("12")
        composeRule.onNodeWithTag(TestTags.FETCH_BUTTON).performClick()

        composeRule.waitUntilDoesNotExist(hasTestTag(TestTags.PROGRESS))

        composeRule.onNodeWithTag(TestTags.CHART).assertIsDisplayed()

//         for fuck sake, I should add espresso and test AndroidView inside composable.
//         I am not willing to do this.
//
//
//         Furthermore! I should run this kind of tests in just integration tests,
//         and not in the end-to-end tests.
//
//         If it happens, I should do sort of this
//         :first check the AndroidView ChartView has type of linear
//         :second perform click
//         composeRule.onNodeWithTag(TestTags.LINE_MODE_BUTTON).performClick()
//         :third check the AndroidView ChartView has type of Bezier

    }
}