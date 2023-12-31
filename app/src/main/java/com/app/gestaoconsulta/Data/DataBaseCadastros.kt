package com.app.gestaoconsulta.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.gestaoconsulta.Data.Daos.CadastroDao
import com.app.gestaoconsulta.Data.Daos.DataCadastradaDao
import com.app.gestaoconsulta.Data.Daos.HoraCadastradaDao
import com.app.gestaoconsulta.Data.Daos.PedidosAgendamentosDao
import com.app.gestaoconsulta.Data.Daos.PedidosPorUsuariosDao
import com.app.gestaoconsulta.Data.Daos.UsuarioDao
import com.app.gestaoconsulta.Data.Entities.AgendamentoPorUsuarioEntity
import com.app.gestaoconsulta.Data.Entities.CadastroEntity
import com.app.gestaoconsulta.Data.Entities.DataCadastradaEntity
import com.app.gestaoconsulta.Data.Entities.HoraCadastradaEntity
import com.app.gestaoconsulta.Data.Entities.PedidosAgendamentosEntity
import com.app.gestaoconsulta.Data.Entities.UsuarioEntity

@Database(entities = [CadastroEntity::class,
    DataCadastradaEntity::class,
    UsuarioEntity::class,
    HoraCadastradaEntity::class,
    PedidosAgendamentosEntity::class,
    AgendamentoPorUsuarioEntity::class], version = 2
)

abstract class DataBaseCadastros : RoomDatabase() {
    abstract fun cadastroDao(): CadastroDao
    abstract fun dataCadastradaDao(): DataCadastradaDao
    abstract fun horaCadastradaDao(): HoraCadastradaDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pediddosAgendamentosDao(): PedidosAgendamentosDao
    abstract fun pedidosPorUsuarios(): PedidosPorUsuariosDao

    companion object {
        val MIGRATION_1_2 = migration_1_2
    }
}