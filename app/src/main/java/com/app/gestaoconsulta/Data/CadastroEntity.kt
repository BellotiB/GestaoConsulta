package com.app.gestaoconsulta.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cadastro")
 data class  CadastroEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "nome") var nome: String,
    @ColumnInfo(name = "especialidade") var especialidade: String
 )

