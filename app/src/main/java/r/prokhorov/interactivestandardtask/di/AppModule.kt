package r.prokhorov.interactivestandardtask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import r.prokhorov.interactivestandardtask.data.PointsRepositoryImpl
import r.prokhorov.interactivestandardtask.data.api.PointsApi
import r.prokhorov.interactivestandardtask.domain.PointsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun providePointsApi(client: OkHttpClient): PointsApi {
        return Retrofit.Builder()
            .baseUrl("https://hr-challenge.interactivestandard.com/")
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