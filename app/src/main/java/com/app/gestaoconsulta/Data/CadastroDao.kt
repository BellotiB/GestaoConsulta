package com.app.gestaoconsulta.Data

import androidx.room.Insert
import androidx.room.Query
import com.app.gestaoconsulta.Model.CadastroMedico


interface CadastroDao {
    @Insert
    suspend fun insertCadastro(cadastro: CadastroMedico)

    @Query("SELECT * FROM cadastro")
    suspend fun getAllCadastros(): MutableList<CadastroMedico>
}