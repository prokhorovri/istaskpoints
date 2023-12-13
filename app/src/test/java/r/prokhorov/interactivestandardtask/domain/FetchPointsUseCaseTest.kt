package r.prokhorov.interactivestandardtask.domain

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import r.prokhorov.interactivestandardtask.data.FakePointsRepository
import r.prokhorov.interactivestandardtask.domain.common.Result

class FetchPointsUseCaseTest {

    private lateinit var fetchPointsUseCase: FetchPointsUseCase
    private lateinit var fakePointsRepository: FakePointsRepository

    @Before
    fun setUp() {
        fakePointsRepository = FakePointsRepository()
        fetchPointsUseCase = FetchPointsUseCase(fakePointsRepository)
    }

    @Test
    fun `Fetch points with valid count`() = runBlocking {
        val result = fetchPointsUseCase(500).first()
        assertThat(result).isInstanceOf(Result.Success::class.java)
    }

    @Test
    fun `Fetch points with 0`() = runBlocking {
        val result = fetchPointsUseCase(0).first()
        assertThat(result).isInstanceOf(Result.Failure::class.java)
    }

    @Test
    fun `Fetch points with 1001`() = runBlocking {
        val result = fetchPointsUseCase(1001).first()
        assertThat(result).isInstanceOf(Result.Failure::class.java)
    }

    @Test
    fun `Fetch points with unexpected server error with valid count`() = runBlocking {
        fakePointsRepository.interruptUnexpectedly = true
        val result = fetchPointsUseCase(500).first()
        assertThat(result).isInstanceOf(Result.Failure::class.java)
    }

}