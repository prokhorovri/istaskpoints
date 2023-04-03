package r.prokhorov.interactivestandardtask.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import r.prokhorov.interactivestandardtask.domain.common.Result
import retrofit2.HttpException
import java.io.IOException

class GetPointsUseCase(val repository: PointsRepository) {
    operator fun invoke(count: Int): Flow<Result<List<Point>>> = flow {
        try {
            emit(Result.Loading)
            val points = repository.getPoints(count).sortedBy(Point::x)
            emit(Result.Success(points))
        } catch (e: HttpException) {
            emit(Result.Failure(e.localizedMessage ?: "Http error code: ${e.code()}"))
        } catch (e: IOException) {
            emit(Result.Failure("Connection problem"))
        }
    }
}
