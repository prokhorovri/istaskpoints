package r.prokhorov.interactivestandardtask.domain

import javax.inject.Inject

class GetSortedPointsUseCase @Inject constructor(private val repository: PointsRepository) {
    operator fun invoke() = repository.getPoints(shouldSort = true)
}