package r.prokhorov.interactivestandardtask.presentation.start

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import r.prokhorov.interactivestandardtask.di.AppModule
import r.prokhorov.interactivestandardtask.presentation.MainActivity
import r.prokhorov.interactivestandardtask.presentation.ScreenRoute
import r.prokhorov.interactivestandardtask.presentation.core.util.TestTags
import r.prokhorov.interactivestandardtask.presentation.ui.theme.InteractiveStandardTaskTheme
import java.net.HttpURLConnection

@OptIn(ExperimentalTestApi::class)
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
        val errorMsg = "huy tebe za vorotnik"
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                .setBody(errorMsg)
        )
        composeRule.onNodeWithTag(TestTags.ERROR_TEXT).assertDoesNotExist()
        composeRule.onNodeWithTag(TestTags.INPUT_FIELD).performTextInput("12")
        composeRule.onNodeWithTag(TestTags.FETCH_BUTTON).performClick()

        composeRule.waitUntilDoesNotExist(hasTestTag(TestTags.PROGRESS))

        composeRule.onNodeWithTag(TestTags.ERROR_TEXT)
            .assertIsDisplayed()
            .assertTextContains(errorMsg)

    }

}