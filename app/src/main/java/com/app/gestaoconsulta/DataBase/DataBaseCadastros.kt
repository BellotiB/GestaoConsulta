package com.app.gestaoconsulta.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.gestaoconsulta.DataBase.Daos.CadastroDao
import com.app.gestaoconsulta.DataBase.Daos.DataCadastradaDao
import com.app.gestaoconsulta.DataBase.Daos.HoraCadastradaDao
import com.app.gestaoconsulta.DataBase.Daos.PedidosAgendamentosDao
import com.app.gestaoconsulta.DataBase.Daos.PedidosPorUsuariosDao
import com.app.gestaoconsulta.DataBase.Daos.UsuarioDao
import com.app.gestaoconsulta.DataBase.Entities.AgendamentoPorUsuarioEntity
import com.app.gestaoconsulta.DataBase.Entities.CadastroEntity
import com.app.gestaoconsulta.DataBase.Entities.DataCadastradaEntity
import com.app.gestaoconsulta.DataBase.Entities.HoraCadastradaEntity
import com.app.gestaoconsulta.DataBase.Entities.PedidosAgendamentosEntity
import com.app.gestaoconsulta.DataBase.Entities.UsuarioEntity

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