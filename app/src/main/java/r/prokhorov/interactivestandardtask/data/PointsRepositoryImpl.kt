package r.prokhorov.interactivestandardtask.data

import r.prokhorov.interactivestandardtask.data.api.PointsApi
import r.prokhorov.interactivestandardtask.domain.Point
import r.prokhorov.interactivestandardtask.domain.PointsRepository

class PointsRepositoryImpl(val api: PointsApi) : PointsRepository {

    override suspend fun getPoints(count: Int): List<Point> {
        return api.getPoints(count).points.map { Point(x = it.x, y = it.y) }
    }

}
