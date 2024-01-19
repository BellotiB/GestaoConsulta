package com.app.gestaoconsulta.DataBase.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "PedidosAgendamento")
data class PedidosAgendamentosEntity (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "idUsuario") var idUsuario: String,
    @ColumnInfo(name = "idMedico") var idMedico: Int,
    @ColumnInfo(name = "dataCadastrada") var dataCadastrada: String,
    @ColumnInfo(name = "horaCadastrada") var horaCadastrada: String,
)