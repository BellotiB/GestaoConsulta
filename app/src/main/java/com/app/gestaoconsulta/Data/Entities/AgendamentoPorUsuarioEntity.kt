package com.app.gestaoconsulta.Data.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("AgendamentosPorUsuario")
data class AgendamentoPorUsuarioEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "idUsuario") var idUsuario: String,
    @ColumnInfo(name = "nomeMedico") var nomeMedico: String,
    @ColumnInfo(name = "horaSelecionada") var horaSelecionada: String,
    @ColumnInfo(name = "dataSelecionada") var dataSelecionada: String,
    )