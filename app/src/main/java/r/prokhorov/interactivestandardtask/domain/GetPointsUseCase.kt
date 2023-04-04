package r.prokhorov.interactivestandardtask.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import r.prokhorov.interactivestandardtask.domain.common.Result
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPointsUseCase @Inject constructor(val repository: PointsRepository) {
    operator fun invoke(count: Int) = flow {
        try {
            // why this - see getPoints() impl
            val points = withContext(Dispatchers.IO) {
                repository.getPoints(count).sortedBy(Point::x) // not so many elements(upt to 1000), actually
            }
            emit(Result.Success(points))
        } catch (e: HttpException) {
            val reason = e.response()?.errorBody()?.string() ?: "Http error code: ${e.code()}"
            emit(Result.Failure(reason))
        } catch (e: IOException) {
            emit(Result.Failure("Connection problem"))
        }
    }
}
