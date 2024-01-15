package com.app.gestaoconsulta.Data

import com.app.gestaoconsulta.Data.Daos.CadastroDao
import com.app.gestaoconsulta.Data.Daos.DataCadastradaDao
import com.app.gestaoconsulta.Data.Daos.HoraCadastradaDao
import com.app.gestaoconsulta.Data.Daos.PedidosAgendamentosDao
import com.app.gestaoconsulta.Data.Daos.PedidosPorUsuariosDao
import com.app.gestaoconsulta.Data.Daos.UsuarioDao
import com.app.gestaoconsulta.Data.Entities.AgendamentoPorUsuarioEntity
import com.app.gestaoconsulta.Data.Entities.CadastroEntity
import com.app.gestaoconsulta.Data.Entities.DataCadastradaEntity
import com.app.gestaoconsulta.Data.Entities.HoraCadastradaEntity
import com.app.gestaoconsulta.Data.Entities.PedidosAgendamentosEntity
import com.app.gestaoconsulta.Data.Entities.UsuarioEntity
import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.SyncApi.ApiService
import com.app.gestaoconsulta.SyncApi.GetPedidosAgendamentos
import com.app.gestaoconsulta.SyncApi.GetUsuarios
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val cadastroDao: CadastroDao,
    private val datacadDao: DataCadastradaDao,
    private val horacadDao: HoraCadastradaDao,
    private val usuarioDao: UsuarioDao,
    private val pedidosDao: PedidosAgendamentosDao,
    private val agendPorUsuarioDao: PedidosPorUsuariosDao,
    private val apiService: ApiService
){
    fun deleteHoraPorData(dataCad : HoraCadastradaEntity){
        horacadDao.delete(dataCad)
    }
     fun deleteCadastroFromDataBase(cad: CadastroEntity){
        cadastroDao.delete(cad)
    }
    fun deleteDataFromDataBase(id: DataCadastradaEntity){
        datacadDao.delete(id)
    }
    fun deleteAllDatasPorCadastro(datas: MutableList<DataCadastradaEntity>){
        datacadDao.deleteAllDatas(datas)
    }
    suspend fun insertCadastro(cadastroEntity: CadastroEntity){
        cadastroDao.insertCadastro(cadastroEntity)
    }
   suspend fun insertDataCadastrada(data: MutableList<DataCadastradaEntity>){
        datacadDao.insertData(data)
    }
    suspend fun insertHrCadastrada(hr: HoraCadastradaEntity){
        horacadDao.insertHora(hr)
    }

    suspend fun insertPedidoAgendamento(agend : AgendamentoPorUsuarioEntity){
        agendPorUsuarioDao.insertAgendUsuario(agend)
    }
    suspend fun insertUsuarioList(hr: MutableList<UsuarioEntity>){
        usuarioDao.insertUsuarioList(hr)
    }
    suspend fun insertPedidoDao(ped: PedidosAgendamentosEntity){
        pedidosDao.insertPedido(ped)
    }

    fun update(horaEntity: HoraCadastradaEntity) {
        horacadDao.update(horaEntity)
    }
    fun getUsersApi(): MutableList<Usuarios> {
        return  apiService.let { GetUsuarios(it).fetchUsuarios()}
    }
    fun getAgendamentos(): MutableList<PedidoAgendamento> {
        return  apiService.let { GetPedidosAgendamentos(it).fetchPedidoAgendamento()}
    }
    val getAllHorasEntity: Flow<MutableList<HoraCadastradaEntity>> = horacadDao.getAllHorasCadastradas()

    val getAllCadastroEntity: Flow<MutableList<CadastroEntity>> = cadastroDao.getAllCadastros()

    val getAllDatasEntity: Flow<MutableList<DataCadastradaEntity>> = datacadDao.getAllDatasCadastradas()

    val getAllUsersEntity: Flow<MutableList<UsuarioEntity>> = usuarioDao.getAllUsuarios()

    val getAllPedidosEntity: Flow<MutableList<PedidosAgendamentosEntity>> = pedidosDao.getAllPedidos()

    val getAllPedidoPorUsuario: Flow<MutableList<AgendamentoPorUsuarioEntity>> = agendPorUsuarioDao.getAllAgendamentos()
}