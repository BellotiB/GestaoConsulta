package com.app.gestaoconsulta.Di

import com.app.gestaoconsulta.SyncApi.ApiService
import com.app.gestaoconsulta.Util.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class NetworkingModule {
    @Provides
    fun providesApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Api.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun providesApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }
}