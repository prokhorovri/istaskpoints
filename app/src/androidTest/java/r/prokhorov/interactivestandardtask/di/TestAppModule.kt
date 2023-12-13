package r.prokhorov.interactivestandardtask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import r.prokhorov.interactivestandardtask.data.PointsRepositoryImpl
import r.prokhorov.interactivestandardtask.data.api.PointsApi
import r.prokhorov.interactivestandardtask.domain.PointsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun providePointsApi(client: OkHttpClient, mockWebServer: MockWebServer): PointsApi {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PointsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePointsRepository(api: PointsApi): PointsRepository {
        return PointsRepositoryImpl(api)
    }
}