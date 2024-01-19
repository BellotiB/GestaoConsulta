package com.app.gestaoconsulta.SyncApi

import com.app.gestaoconsulta.DataBase.Entities.HoraCadastradaEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetHorasToServer @Inject constructor(
    private val apiService: ApiService
) {
    fun fecthHoraCadastrada(list: MutableList<HoraCadastradaEntity>) {
        val result = runCatching { apiService.setHorasCadastradas(list).execute() }
        result.onSuccess {
            it.isSuccessful
        }
        result.onFailure {
            it.printStackTrace()
        }
    }
}
