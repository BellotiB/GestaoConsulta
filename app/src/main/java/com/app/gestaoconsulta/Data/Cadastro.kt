package com.app.gestaoconsulta.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cadastro")
 data class  Cadastro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "especialidade") val especialidade: String
 )

