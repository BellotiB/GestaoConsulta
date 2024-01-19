package com.app.gestaoconsulta.DataBase

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Adicione a nova coluna com a propriedade notNull definida como true
        database.execSQL("ALTER TABLE DatasCadastradas ADD COLUMN idDataCadastrada TEXT NOT NULL DEFAULT 'undefined'")
    }
}