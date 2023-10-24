package com.app.gestaoconsulta.Data.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuario")
 data class  UsuarioEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "nome") var nome: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "idUsuario") var idUsuario: String,
    @ColumnInfo(name = "telefone") var telefone: String
 )

