package com.app.gestaoconsulta.Data.Daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.gestaoconsulta.Data.Entities.AgendamentoPorUsuarioEntity
import com.app.gestaoconsulta.Data.Entities.UsuarioEntity
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