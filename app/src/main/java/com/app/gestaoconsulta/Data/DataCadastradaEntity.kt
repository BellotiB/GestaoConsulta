package com.app.gestaoconsulta.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DatasCadastradas")
data class DataCadastradaEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "idCadastro") var idCadastro: Int,
    @ColumnInfo(name = "startDate") var startDate: String,
    @ColumnInfo(name = "startHora") var startHora: String,
    @ColumnInfo(name = "endDate") var endDate: String,
    @ColumnInfo(name = "endHora") var endHora: String,
    @ColumnInfo(name = "periodoAtendimento") var periodoAtendimento: String,
)