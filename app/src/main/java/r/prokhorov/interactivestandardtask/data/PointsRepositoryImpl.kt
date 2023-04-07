package r.prokhorov.interactivestandardtask.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import r.prokhorov.interactivestandardtask.data.api.PointsApi
import r.prokhorov.interactivestandardtask.domain.Point
import r.prokhorov.interactivestandardtask.domain.PointsRepository
import r.prokhorov.interactivestandardtask.domain.common.Result
import retrofit2.HttpException
import java.io.IOException

class PointsRepositoryImpl(val api: PointsApi) : PointsRepository {

    private var localStoragePoints = emptyList<Point>()

    override fun fetchPoints(count: Int) = flow {
        try {
            // the Retrofit method itself already executes in the IO context
            val response = api.getPoints(count)

            // instead of onEach savecashe
            localStoragePoints = response.points.map { Point(x = it.x, y = it.y) }

            emit(Result.Success(localStoragePoints))
        } catch (e: HttpException) {
            val reason = e.response()?.errorBody()?.string() ?: "Http error code: ${e.code()}"
            emit(Result.Failure(reason))
        } catch (e: IOException) {
            emit(Result.Failure("Connection problem"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getPoints(shouldSort: Boolean): Flow<Result<List<Point>>> {
        TODO("Not yet implemented")
    }

}
