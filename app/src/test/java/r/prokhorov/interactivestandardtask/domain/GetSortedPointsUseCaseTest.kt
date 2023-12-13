package r.prokhorov.interactivestandardtask.domain

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import r.prokhorov.interactivestandardtask.data.FakePointsRepository
import r.prokhorov.interactivestandardtask.domain.common.Result

class GetSortedPointsUseCaseTest {
    private lateinit var fakePointsRepository: FakePointsRepository
    private lateinit var getSortedPointsUseCase: GetSortedPointsUseCase

    @Before
    fun setUp() {
        fakePointsRepository = FakePointsRepository()
        getSortedPointsUseCase = GetSortedPointsUseCase(fakePointsRepository)
        runBlocking { fakePointsRepository.fetchPoints(10).first() }
    }

    @Test
    fun `Get sorted points, correct order`() = runBlocking{
        val response = getSortedPointsUseCase().first()

        assertThat(response).isInstanceOf(Result.Success::class.java)

        val points = (response as Result.Success<List<Point>>).data
        for(i in 0..points.lastIndex - 1) {
            assertThat(points[i].x).isLessThan(points[i + 1].x)
        }
    }
}