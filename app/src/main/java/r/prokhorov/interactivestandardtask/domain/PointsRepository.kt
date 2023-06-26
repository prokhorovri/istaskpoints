package r.prokhorov.interactivestandardtask.domain

import r.prokhorov.interactivestandardtask.domain.common.Result

interface PointsRepository {
    suspend fun fetchPoints(count: Int): Result<List<Point>>
    suspend fun getPoints(shouldSort: Boolean = false): Result<List<Point>>
}