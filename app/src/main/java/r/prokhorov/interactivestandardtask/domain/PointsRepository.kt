package r.prokhorov.interactivestandardtask.domain

import kotlinx.coroutines.flow.Flow
import r.prokhorov.interactivestandardtask.domain.common.Result

interface PointsRepository {
    fun fetchPoints(count: Int): Flow<Result<List<Point>>>
    fun getPoints(shouldSort: Boolean = false): Flow<Result<List<Point>>>
}