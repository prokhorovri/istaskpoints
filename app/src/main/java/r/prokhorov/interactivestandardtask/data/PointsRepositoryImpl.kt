package r.prokhorov.interactivestandardtask.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import r.prokhorov.interactivestandardtask.data.api.PointsApi
import r.prokhorov.interactivestandardtask.domain.Point
import r.prokhorov.interactivestandardtask.domain.PointsRepository

class PointsRepositoryImpl(val api: PointsApi) : PointsRepository {

    override suspend fun getPoints(count: Int) = withContext(Dispatchers.IO) {
        // the Retrofit method itself already executes in the IO context,
        // but in order for the mapping to also be performed in IO(Default is also could be applied),
        // the context is changed.
        // this doesn't require any significant additional overhead.
        api.getPoints(count).points.map { Point(x = it.x, y = it.y) }
    }

}
