package com.app.gestaoconsulta.Data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cadastro::class], version = 1, exportSchema = false)
abstract class DataBaseCadastros : RoomDatabase() {
    abstract fun cadastroDao():CadastroDao
}