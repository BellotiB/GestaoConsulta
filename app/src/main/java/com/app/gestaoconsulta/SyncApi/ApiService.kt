package com.app.gestaoconsulta.SyncApi

import com.app.gestaoconsulta.DataBase.Entities.CadastroEntity
import com.app.gestaoconsulta.DataBase.Entities.DataCadastradaEntity
import com.app.gestaoconsulta.DataBase.Entities.HoraCadastradaEntity
import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.Model.Usuarios
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ApiService {
    @GET("usuario.json")
    fun getUsuarios(): Call<Map<String, Usuarios>>

    @GET("pedido_agendamento.json")
    fun getPedidoAgendamento(): Call<MutableList<PedidoAgendamento>>

    @PUT("cadastro-medicos.json")
    fun setCadastroMedico( @Body cadastroEntity: MutableList<CadastroEntity>): Call<MutableList<CadastroEntity>>

    @PUT("datas-cadastradas.json")
    fun setDataCadastrada(@Body dataCadastradaEntity: MutableList<DataCadastradaEntity>):Call<MutableList<DataCadastradaEntity>>
    @PUT("horas-cadastradas.json")
    fun setHorasCadastradas(@Body dataCadastradaEntity: MutableList<HoraCadastradaEntity>):Call<MutableList<HoraCadastradaEntity>>
}