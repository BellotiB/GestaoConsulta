package com.app.gestaoconsulta.SyncApi

import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.Model.Usuarios
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("usuario.json")
    fun getUsuarios(): Call<Map<String, Usuarios>>

    @GET("pedido_agendamento.json")
    fun getPedidoAgendamento(): Call<Map<String, MutableList<PedidoAgendamento>>>
}