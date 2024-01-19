package com.app.gestaoconsulta.SyncApi

import com.app.gestaoconsulta.Model.Usuarios
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class GetUsuarios @Inject constructor(
    private val apiService: ApiService
) {
    fun fetchUsuarios(): MutableList<Usuarios> {
        return runCatching {
            val response = apiService.getUsuarios().execute()
            check(response.isSuccessful) { "Erro ao obter usuários: ${response.message()}"}

            response.body()?.values?.toMutableList() ?: mutableListOf()
        }.getOrElse {
            mutableListOf()
        }
    }
}





