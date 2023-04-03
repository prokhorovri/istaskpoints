package r.prokhorov.interactivestandardtask.domain

interface PointsRepository {
    suspend fun getPoints(count: Int): List<Point>
}