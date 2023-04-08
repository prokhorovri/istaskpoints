package r.prokhorov.interactivestandardtask.presentation.start

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import r.prokhorov.interactivestandardtask.R
import r.prokhorov.interactivestandardtask.presentation.ScreenRoute
import r.prokhorov.interactivestandardtask.presentation.core.LightDarkPreviews
import r.prokhorov.interactivestandardtask.presentation.ui.theme.InteractiveStandardTaskTheme

@Composable
fun StartScreen(
    navController: NavController,
    startViewModel: StartViewModel = hiltViewModel()
) {

    val state by startViewModel.state
    val isFetched = startViewModel.isFetched.collectAsState(false)
    val input by startViewModel.inputState.collectAsState()

    if (isFetched.value) {
        LaunchedEffect(Unit) {
            navController.navigate(ScreenRoute.Chart.route)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Center))
        } else {
            StartScreenLayout(
                input = input,
                isInputError = state.isInputError,
                error = state.error,
                onInputChanged = { startViewModel.updateCountInput(it) },
                onButtonClicked = { startViewModel.fetchPoints() },
                modifier = Modifier.align(Center)
            )
        }
    }
}

@Composable
fun StartScreenLayout(
    input: String,
    isInputError: Boolean,
    error: String,
    onInputChanged: (String) -> Unit,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.fetch_information_text))
        Spacer(modifier = Modifier.size(24.dp))
        OutlinedTextField(
            value = input,
            singleLine = true,
            onValueChange = onInputChanged,
            label = { Text(stringResource(R.string.fetch_input_hint)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = { onButtonClicked() }
            ),
            isError = isInputError,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = VisualTransformation.None
        )

        // todo; instead snackbar and textfieldview errors
        if (error.isNotBlank()) {
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = error,
                color = MaterialTheme.colors.error
            )
        }

        Spacer(modifier = Modifier.size(32.dp))

        Button(onClick = onButtonClicked) {
            Text(text = stringResource(id = R.string.fetch_button))
        }
    }
}

@LightDarkPreviews
@Composable
fun StartScreenLayoutPreview() {
    InteractiveStandardTaskTheme {
        Surface {
            StartScreenLayout(
                input = "",
                isInputError = false,
                error = "",
                onInputChanged = { /*empty*/ },
                onButtonClicked = { /*empty*/ }
            )
        }
    }
}
