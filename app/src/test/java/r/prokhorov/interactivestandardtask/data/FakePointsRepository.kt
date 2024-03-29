package r.prokhorov.interactivestandardtask.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.yield
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import r.prokhorov.interactivestandardtask.domain.Point
import r.prokhorov.interactivestandardtask.domain.PointsRepository
import r.prokhorov.interactivestandardtask.domain.common.Result
import retrofit2.HttpException
import retrofit2.Response
import kotlin.random.Random.Default.nextFloat

class FakePointsRepository : PointsRepository {

    private var points = listOf<Point>()

    var interruptUnexpectedly = false

    override fun fetchPoints(count: Int): Flow<Result<List<Point>>> {
        return flow {
            try {
                points = pupolatePoints(count)
                emit(Result.Success(points))
            } catch (e: HttpException) {
                val reason = e.response()?.errorBody()?.string()
                    ?: "Http error code: ${e.code()}"
                emit(Result.Failure(reason))
            }
        }
    }

    private suspend fun pupolatePoints(count: Int): List<Point> {
        if (count <= 0) throw HttpException(
            Response.error<ResponseBody>
                (
                403,
                "count should be more than 0".toResponseBody("plain/text".toMediaTypeOrNull())
            )
        )

        if (count > 1000) throw HttpException(
            Response.error<ResponseBody>
                (
                403,
                "count should be less than or equal to 1000".toResponseBody("plain/text".toMediaTypeOrNull())
            )
        )

        val currentPoints = mutableListOf<Point>()
        (0..count).forEach {
            yield()

            currentPoints.add(Point(nextFloat(), nextFloat()))

            if (interruptUnexpectedly) {
                throw HttpException(
                    Response.error<ResponseBody>
                        (
                        500,
                        "just unexpected server error".toResponseBody("plain/text".toMediaTypeOrNull())
                    )
                )
            }
        }

        return currentPoints
    }

    override fun getPoints(shouldSort: Boolean): Flow<Result<List<Point>>> {
        return flow {
            if (points.isNotEmpty()) {
                val points = if (shouldSort) {
                    points.sortedBy(Point::x)
                } else {
                    points.shuffled()
                }
                emit(Result.Success(points))
            } else {
                emit(Result.Failure("No data"))
            }
        }
    }
}