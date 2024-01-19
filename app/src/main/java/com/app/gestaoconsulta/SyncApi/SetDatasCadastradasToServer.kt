package com.app.gestaoconsulta.SyncApi

import com.app.gestaoconsulta.DataBase.Entities.DataCadastradaEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetDatasCadastradasToServer @Inject constructor(
    private val apiService: ApiService
) {
    fun fecthDataCadastrada(list: MutableList<DataCadastradaEntity>){
        val result = runCatching {apiService.setDataCadastrada(list).execute() }

        result.onSuccess { response ->
            response.isSuccessful
        }
        result.onFailure { respnose ->
            respnose.printStackTrace()
        }
    }
}