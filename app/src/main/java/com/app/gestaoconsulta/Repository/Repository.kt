package com.app.gestaoconsulta.Repository

import SetCadastroMedicoToServer
import com.app.gestaoconsulta.DataBase.Daos.CadastroDao
import com.app.gestaoconsulta.DataBase.Daos.DataCadastradaDao
import com.app.gestaoconsulta.DataBase.Daos.HoraCadastradaDao
import com.app.gestaoconsulta.DataBase.Daos.PedidosAgendamentosDao
import com.app.gestaoconsulta.DataBase.Daos.PedidosPorUsuariosDao
import com.app.gestaoconsulta.DataBase.Daos.UsuarioDao
import com.app.gestaoconsulta.DataBase.Entities.AgendamentoPorUsuarioEntity
import com.app.gestaoconsulta.DataBase.Entities.CadastroEntity
import com.app.gestaoconsulta.DataBase.Entities.DataCadastradaEntity
import com.app.gestaoconsulta.DataBase.Entities.HoraCadastradaEntity
import com.app.gestaoconsulta.DataBase.Entities.PedidosAgendamentosEntity
import com.app.gestaoconsulta.DataBase.Entities.UsuarioEntity
import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.SyncApi.ApiService
import com.app.gestaoconsulta.SyncApi.GetPedidosAgendamentos
import com.app.gestaoconsulta.SyncApi.GetUsuarios
import com.app.gestaoconsulta.SyncApi.SetDatasCadastradasToServer
import com.app.gestaoconsulta.SyncApi.SetHorasToServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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

    fun update(horaEntity: HoraCadastradaEntity) {
        horacadDao.update(horaEntity)
    }
    fun getUsersApi(): MutableList<Usuarios> {
        return  apiService.let {GetUsuarios(it).fetchUsuarios()}
    }
    fun getAgendamentos(): MutableList<PedidoAgendamento> {
        return  apiService.let {GetPedidosAgendamentos(it).fetchPedidoAgendamento()}
    }
    suspend fun setMedicos(list: MutableList<CadastroEntity>) {
        withContext(Dispatchers.IO){
            apiService.let {SetCadastroMedicoToServer(it).fetchCadastroMedico(list)}
        }
    }
    suspend fun setDatas(list: MutableList<DataCadastradaEntity>) {
        withContext(Dispatchers.IO){
            apiService.let {SetDatasCadastradasToServer(it).fecthDataCadastrada(list)}
        }
    }
    suspend fun setHoras(list: MutableList<HoraCadastradaEntity>) {
        withContext(Dispatchers.IO){
            apiService.let {SetHorasToServer(it).fecthHoraCadastrada(list)}
        }
    }
    val getAllHorasEntity: Flow<MutableList<HoraCadastradaEntity>> = horacadDao.getAllHorasCadastradas()

    val getAllCadastroEntity: Flow<MutableList<CadastroEntity>> = cadastroDao.getAllCadastros()

    val getAllDatasEntity: Flow<MutableList<DataCadastradaEntity>> = datacadDao.getAllDatasCadastradas()

    val getAllUsersEntity: Flow<MutableList<UsuarioEntity>> = usuarioDao.getAllUsuarios()

    val getAllPedidosEntity: Flow<MutableList<PedidosAgendamentosEntity>> = pedidosDao.getAllPedidos()

    val getAllPedidoPorUsuario: Flow<MutableList<AgendamentoPorUsuarioEntity>> = agendPorUsuarioDao.getAllAgendamentos()
}