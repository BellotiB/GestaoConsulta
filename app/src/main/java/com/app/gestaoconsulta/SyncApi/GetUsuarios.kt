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
        return try {
            val response = apiService.getUsuarios().execute()
            if (response.isSuccessful) {
                response.body()?.values?.let { usuarios ->
                return  usuarios.toMutableList()
                } ?: mutableListOf()
            } else {
                throw Exception("Erro ao obter usuários: ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Erro ao obter usuários: ${e.message}")
        }
    }
}





