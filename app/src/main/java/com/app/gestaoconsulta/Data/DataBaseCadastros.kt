package com.app.gestaoconsulta.Data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CadastroEntity::class,DataCadastradaEntity::class], version = 2)
abstract class DataBaseCadastros : RoomDatabase() {
    abstract fun cadastroDao():CadastroDao
    abstract fun dataCadastradaDao():DataCadastradaDao

    companion object {
        val MIGRATION_1_2 = migration_1_2
    }
}