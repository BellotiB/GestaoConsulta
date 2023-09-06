package com.app.gestaoconsulta.Data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val cadastroDao: CadastroDao )  {

    suspend fun insertCadastro(cadastro: Cadastro){
        cadastroDao.insertCadastro(cadastro)
    }

    val getAllCadastro: Flow<MutableList<Cadastro>> = cadastroDao.getAllCadastros()

}