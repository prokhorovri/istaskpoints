package r.prokhorov.interactivestandardtask.domain.common

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val reason: String) : Result<Nothing>() // reason String is for simplicity's sake
}
