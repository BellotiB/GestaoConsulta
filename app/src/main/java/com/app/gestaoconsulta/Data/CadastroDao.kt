package com.app.gestaoconsulta.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CadastroDao {
    @Insert
    suspend fun insertCadastro(cadastroEntity: CadastroEntity)

    @Query("SELECT * FROM cadastro")
    fun getAllCadastros(): Flow<MutableList<CadastroEntity>>

    @Delete
    fun delete(cad: CadastroEntity)
}