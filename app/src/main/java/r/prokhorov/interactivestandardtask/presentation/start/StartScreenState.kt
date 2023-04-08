package r.prokhorov.interactivestandardtask.presentation.start

data class StartScreenState(
    val isLoading: Boolean = false,
    val isInputError: Boolean = false,
    val error: String = "" // todo: no textfield error support yet, just a text
)