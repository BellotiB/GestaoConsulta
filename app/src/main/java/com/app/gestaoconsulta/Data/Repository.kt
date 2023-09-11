package com.app.gestaoconsulta.Data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val cadastroDao: CadastroDao,
    private val datacadDao: DataCadastradaDao
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
   suspend fun insertDataCadastrada(data: MutableList<DataCadastradaEntity>){
        datacadDao.insertData(data)
    }

    val getAllCadastroEntity: Flow<MutableList<CadastroEntity>> = cadastroDao.getAllCadastros()

    val getAllDatasEntity: Flow<MutableList<DataCadastradaEntity>> = datacadDao.getAllDatasCadastradas()
}