package com.app.gestaoconsulta.Data

import com.app.gestaoconsulta.Model.CadastroMedico
import javax.inject.Inject

class Repository @Inject constructor(private val cadastroDao: CadastroDao )  {

    suspend fun insertCadastro(cadastro: Cadastro){
        cadastroDao.insertCadastro(cadastro)
    }

    suspend fun getAllCadastro(): MutableList<Cadastro> {
        return cadastroDao.getAllCadastros()
    }
}