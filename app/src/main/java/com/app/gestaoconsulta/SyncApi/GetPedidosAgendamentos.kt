package com.app.gestaoconsulta.SyncApi


import com.app.gestaoconsulta.Model.PedidoAgendamento

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPedidosAgendamentos @Inject constructor(
    private val apiService: ApiService
) {
    fun fetchPedidoAgendamento(): MutableList<PedidoAgendamento> {
        return try {
            val response = apiService.getPedidoAgendamento().execute()
            if (response.isSuccessful) {
                response.body()?.values?.let { pedidos ->
                    return  pedidos.flatten().toMutableList()
                } ?: mutableListOf()
            } else {
                throw Exception("Erro ao obter pedidos de agendamento: ${response.message()}")
            }
        } catch (e: Exception) {
              throw Exception("Erro ao obter pedidos de agendamento: ${e.message}")
        }
    }


}





