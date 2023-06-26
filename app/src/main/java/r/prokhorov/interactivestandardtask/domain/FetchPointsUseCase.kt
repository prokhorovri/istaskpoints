package r.prokhorov.interactivestandardtask.domain

import javax.inject.Inject

class FetchPointsUseCase @Inject constructor(private val repository: PointsRepository) {
    suspend operator fun invoke(count: Int) = repository.fetchPoints(count)
}
