package com.app.gestaoconsulta.Data.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HorasCadastradas")
data class HoraCadastradaEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "idDataCadastrada") var idDataCadastrada: String,
    @ColumnInfo(name = "cinco") var cinco: Boolean,
    @ColumnInfo(name = "cincoQuinze") var cincoQuinze: Boolean,
    @ColumnInfo(name = "cincoTrinta") var cincoTrinta: Boolean,
    @ColumnInfo(name = "cincoQuarentaCinco") var cincoQuarentaCinco: Boolean,

    @ColumnInfo(name = "seis") var seis: Boolean,
    @ColumnInfo(name = "seisQuinze") var seisQuinze: Boolean,
    @ColumnInfo(name = "seisTrinta") var seisTrinta: Boolean,
    @ColumnInfo(name = "seisQuarentaCinco") var seisQuarentaCinco: Boolean,

    @ColumnInfo(name = "sete") var sete: Boolean,
    @ColumnInfo(name = "seteQuinze") var seteQuinze: Boolean,
    @ColumnInfo(name = "seteTrinta") var seteTrinta: Boolean,
    @ColumnInfo(name = "seteQuarentaCinco") var seteQuarentaCinco: Boolean,

    @ColumnInfo(name = "oito") var oito: Boolean,
    @ColumnInfo(name = "oitoQuinze") var oitoQuinze: Boolean,
    @ColumnInfo(name = "oitoTrinta") var oitoTrinta: Boolean,
    @ColumnInfo(name = "oitoQuarentaCinco") var oitoQuarentaCinco: Boolean,

    @ColumnInfo(name = "nove") var nove: Boolean,
    @ColumnInfo(name = "noveQuinze") var noveQuinze: Boolean,
    @ColumnInfo(name = "noveTrinta") var noveTrinta: Boolean,
    @ColumnInfo(name = "noveQuarentaCinco") var noveQuarentaCinco: Boolean,

    @ColumnInfo(name = "dez") var dez: Boolean,
    @ColumnInfo(name = "dezQuinze") var dezQuinze: Boolean,
    @ColumnInfo(name = "dezTrinta") var dezTrinta: Boolean,
    @ColumnInfo(name = "dezQuarentaCinco") var dezQuarentaCinco: Boolean,

    @ColumnInfo(name = "onze") var onze: Boolean,
    @ColumnInfo(name = "onzeQuinze") var onzeQuinze: Boolean,
    @ColumnInfo(name = "onzeTrinta") var onzeTrinta: Boolean,
    @ColumnInfo(name = "onzeQuarentaCinco") var onzeQuarentaCinco: Boolean,



)