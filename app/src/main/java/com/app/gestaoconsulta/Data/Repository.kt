package com.app.gestaoconsulta.Data

import com.app.gestaoconsulta.Model.CadastroMedico
import javax.inject.Inject

class Repository @Inject constructor()  {

    private val cadastroDao: CadastroDao? = null
    //suspend fun insertCadastro(cadastro: CadastroMedico){
        //cadastroDao.insertCadastro(cadastro)
   // }

    suspend fun getAllCadastro(): MutableList<CadastroMedico>? {
        return cadastroDao?.getAllCadastros()
    }
}