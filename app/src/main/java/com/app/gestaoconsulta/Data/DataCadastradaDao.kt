package com.app.gestaoconsulta.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DataCadastradaDao {
    @Insert
    suspend fun insertData(datasCadastradas:MutableList<DataCadastradaEntity>)

    @Query("SELECT * FROM datascadastradas")
    fun getAllDatasCadastradas(): Flow<MutableList<DataCadastradaEntity>>

   @Delete
    fun delete(id: DataCadastradaEntity)
}