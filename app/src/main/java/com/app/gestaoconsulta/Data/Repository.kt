package com.app.gestaoconsulta.Data

import com.app.gestaoconsulta.Data.Daos.CadastroDao
import com.app.gestaoconsulta.Data.Daos.DataCadastradaDao
import com.app.gestaoconsulta.Data.Daos.HoraCadastradaDao
import com.app.gestaoconsulta.Data.Entities.CadastroEntity
import com.app.gestaoconsulta.Data.Entities.DataCadastradaEntity
import com.app.gestaoconsulta.Data.Entities.HoraCadastradaEntity
import com.app.gestaoconsulta.Model.HorariosCadastrados
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val cadastroDao: CadastroDao,
    private val datacadDao: DataCadastradaDao,
    private val horacadDao: HoraCadastradaDao
    ){

    suspend fun insertCadastro(cadastroEntity: CadastroEntity){
        cadastroDao.insertCadastro(cadastroEntity)
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
   suspend fun insertDataCadastrada(data: MutableList<DataCadastradaEntity>){
        datacadDao.insertData(data)
    }
    suspend fun insertHrCadastrada(hr: HoraCadastradaEntity){
        horacadDao.insertHora(hr)
    }

    val getAllHorasEntity: Flow<MutableList<HoraCadastradaEntity>> = horacadDao.getAllHorasCadastradas()

    val getAllCadastroEntity: Flow<MutableList<CadastroEntity>> = cadastroDao.getAllCadastros()

    val getAllDatasEntity: Flow<MutableList<DataCadastradaEntity>> = datacadDao.getAllDatasCadastradas()
}