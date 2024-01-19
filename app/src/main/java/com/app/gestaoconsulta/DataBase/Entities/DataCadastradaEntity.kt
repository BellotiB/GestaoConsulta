package com.app.gestaoconsulta.DataBase.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DatasCadastradas")
data class DataCadastradaEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "idCadastro") var idCadastro: Int,
    @ColumnInfo(name = "idDataCadastrada") var idDataCadastrada: String,
    @ColumnInfo(name = "dataAtendimento") var dataAtendimento: String,
)