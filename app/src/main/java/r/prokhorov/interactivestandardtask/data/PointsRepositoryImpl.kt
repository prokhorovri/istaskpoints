package r.prokhorov.interactivestandardtask.data

import r.prokhorov.interactivestandardtask.data.api.PointsApi
import r.prokhorov.interactivestandardtask.domain.Point
import r.prokhorov.interactivestandardtask.domain.PointsRepository
import r.prokhorov.interactivestandardtask.domain.common.Result
import retrofit2.HttpException
import java.io.IOException

class PointsRepositoryImpl(private val api: PointsApi) : PointsRepository {

    private var localStoragePoints = emptyList<Point>() // haha

    // make this method suspend apropriately
    override suspend fun fetchPoints(count: Int): Result<List<Point>> = try {
        // the Retrofit method itself already executes in the IO context
        val response = api.getPoints(count)
        localStoragePoints = response.points.map { Point(x = it.x, y = it.y) }
        Result.Success(localStoragePoints)
    } catch (e: HttpException) {
        val reason = e.response()?.errorBody()?.string() ?: "Http error code: ${e.code()}"
        Result.Failure(reason)
    } catch (e: IOException) {
        Result.Failure("Connection problem")
    }

    override suspend fun getPoints(shouldSort: Boolean): Result<List<Point>> {
        if (localStoragePoints.isEmpty()) {
            return Result.Failure("No data")
        }

        val points = if (shouldSort) {
            localStoragePoints.sortedBy(Point::x)
        } else {
            localStoragePoints
        }
        return Result.Success(points)
    }

}
