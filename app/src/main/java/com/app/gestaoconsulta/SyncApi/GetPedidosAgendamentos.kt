package com.app.gestaoconsulta.SyncApi


import com.app.gestaoconsulta.Model.PedidoAgendamento

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPedidosAgendamentos @Inject constructor(
    private val apiService: ApiService
) {
    fun fetchPedidoAgendamento(): MutableList<PedidoAgendamento> {
        return runCatching {
            val response = apiService.getPedidoAgendamento().execute()
            check(response.isSuccessful){"Erro ao obter pedido Agendamento: ${response.message()}"}

            response.body()?: mutableListOf()
        }.getOrElse {
            print(it.message)
            mutableListOf()
        }
    }
}





