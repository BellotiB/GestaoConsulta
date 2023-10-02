package com.app.gestaoconsulta.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.gestaoconsulta.Data.Daos.CadastroDao
import com.app.gestaoconsulta.Data.Daos.DataCadastradaDao
import com.app.gestaoconsulta.Data.Daos.HoraCadastradaDao
import com.app.gestaoconsulta.Data.Entities.CadastroEntity
import com.app.gestaoconsulta.Data.Entities.DataCadastradaEntity
import com.app.gestaoconsulta.Data.Entities.HoraCadastradaEntity

@Database(entities = [CadastroEntity::class,
    DataCadastradaEntity::class,
    HoraCadastradaEntity::class], version = 2)

abstract class DataBaseCadastros : RoomDatabase() {
    abstract fun cadastroDao(): CadastroDao
    abstract fun dataCadastradaDao(): DataCadastradaDao
    abstract fun horaCadastradaDao(): HoraCadastradaDao

    companion object {
        val MIGRATION_1_2 = migration_1_2
    }
}