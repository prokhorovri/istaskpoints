package r.prokhorov.interactivestandardtask.data.api

import r.prokhorov.interactivestandardtask.data.PointsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsApi {

    @GET("points")
    suspend fun getPoints(@Query("count") count: Int): PointsResponse
}