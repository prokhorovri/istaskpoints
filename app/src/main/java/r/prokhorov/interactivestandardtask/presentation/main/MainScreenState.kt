package r.prokhorov.interactivestandardtask.presentation.main

data class MainScreenState(
    val isLoading: Boolean = false,
    val isPointsFetched: Boolean = false,
    val isInputError: Boolean = false,
    val error: String = "" // todo: no textfield error support yet, just a text
)