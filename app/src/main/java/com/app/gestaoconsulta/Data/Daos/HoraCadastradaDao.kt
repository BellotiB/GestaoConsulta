package com.app.gestaoconsulta.Data.Daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.gestaoconsulta.Data.Entities.HoraCadastradaEntity
import com.app.gestaoconsulta.Model.HorariosCadastrados
import kotlinx.coroutines.flow.Flow

@Dao
interface HoraCadastradaDao {
    @Insert
    suspend fun insertHora(datasCadastradas: HoraCadastradaEntity)

    @Query("SELECT * FROM HorasCadastradas")
    fun getAllHorasCadastradas(): Flow<MutableList<HoraCadastradaEntity>>

   @Delete
    fun delete(id: HoraCadastradaEntity)
    @Update
    fun update(id: HoraCadastradaEntity)
    @Delete
    fun deleteAllHoras(datasCadastradas: MutableList<HoraCadastradaEntity>)
}