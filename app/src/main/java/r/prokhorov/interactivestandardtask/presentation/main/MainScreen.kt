package r.prokhorov.interactivestandardtask.presentation.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import r.prokhorov.interactivestandardtask.R
import r.prokhorov.interactivestandardtask.presentation.ui.theme.InteractiveStandardTaskTheme

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val state by mainViewModel.state
    val input by mainViewModel.inputState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Center))
        } else {
            Column(
                modifier = Modifier
                    .align(Center)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.fetch_information_text))
                Spacer(modifier = Modifier.size(24.dp))
                OutlinedTextField(
                    value = input,
                    singleLine = true,
                    onValueChange = { newValue ->
                        mainViewModel.updateCountInput(newValue)
                    },
                    label = { Text(stringResource(R.string.fetch_input_hint)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(
                        onGo = { mainViewModel.fetchPoints() }
                    ),
                    isError = state.isInputError,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = VisualTransformation.None
                )

                // todo; instead snackbar and textfieldview errors
                if (state.error.isNotBlank()) {
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error
                    )
                }

                Spacer(modifier = Modifier.size(32.dp))

                Button(
                    onClick = { mainViewModel.fetchPoints() }
                ) {
                    Text(text = stringResource(id = R.string.fetch_button))
                }
            }

        }
    }
}


@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PointsFetchPreview() {
    InteractiveStandardTaskTheme {
        Surface {
            MainScreen()
        }
    }
}
