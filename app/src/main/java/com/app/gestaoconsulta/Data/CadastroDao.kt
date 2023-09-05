package com.app.gestaoconsulta.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.gestaoconsulta.Model.CadastroMedico

@Dao
interface CadastroDao {
    @Insert
    suspend fun insertCadastro(cadastro: Cadastro)

    @Query("SELECT * FROM cadastro")
    suspend fun getAllCadastros(): MutableList<Cadastro>
}