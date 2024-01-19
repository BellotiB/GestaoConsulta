package com.app.gestaoconsulta.DataBase.Daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.gestaoconsulta.DataBase.Entities.AgendamentoPorUsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidosPorUsuariosDao {
    @Insert
    suspend fun insertAgendUsuario(agend: AgendamentoPorUsuarioEntity)

    @Query("SELECT * FROM AgendamentosPorUsuario")
    fun getAllAgendamentos(): Flow<MutableList<AgendamentoPorUsuarioEntity>>

   @Delete
    fun delete(id: AgendamentoPorUsuarioEntity)
}