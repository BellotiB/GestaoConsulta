package com.app.gestaoconsulta.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CadastroDao {
    @Insert
    suspend fun insertCadastro(cadastro: Cadastro)

    @Query("SELECT * FROM cadastro")
    fun getAllCadastros(): Flow<MutableList<Cadastro>>
    @Query("SELECT * FROM cadastro WHERE id = :id")
    fun getCadastrosById(id : Int): Flow<Cadastro>
}