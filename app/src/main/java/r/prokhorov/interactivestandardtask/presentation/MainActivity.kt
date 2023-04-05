package r.prokhorov.interactivestandardtask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import dagger.hilt.android.AndroidEntryPoint
import r.prokhorov.interactivestandardtask.presentation.main.MainScreen
import r.prokhorov.interactivestandardtask.presentation.ui.theme.InteractiveStandardTaskTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InteractiveStandardTaskTheme {
                Surface {
                    MainScreen()
                }
            }
        }
    }
}
