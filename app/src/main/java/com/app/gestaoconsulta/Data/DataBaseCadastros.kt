package com.app.gestaoconsulta.Data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CadastroEntity::class,DataCadastradaEntity::class], version = 1)
abstract class DataBaseCadastros : RoomDatabase() {
    abstract fun cadastroDao():CadastroDao
    abstract fun dataCadastradaDao():DataCadastradaDao
}