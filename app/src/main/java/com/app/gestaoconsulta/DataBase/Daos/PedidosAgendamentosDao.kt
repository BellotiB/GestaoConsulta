package com.app.gestaoconsulta.DataBase.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.gestaoconsulta.DataBase.Entities.PedidosAgendamentosEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface PedidosAgendamentosDao {
    @Insert
    suspend fun insertPedido(pedidos: PedidosAgendamentosEntity)

    @Query("SELECT * FROM PedidosAgendamento")
    fun getAllPedidos(): Flow<MutableList<PedidosAgendamentosEntity>>
}