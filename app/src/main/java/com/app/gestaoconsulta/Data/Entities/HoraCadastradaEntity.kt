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

    @ColumnInfo(name = "doze") var doze: Boolean,
    @ColumnInfo(name = "dozeQuinze") var dozeQuinze: Boolean,
    @ColumnInfo(name = "dozeTrinta") var dozeTrinta: Boolean,
    @ColumnInfo(name = "dozeQuarentaCinco") var dozeQuarentaCinco: Boolean,

    @ColumnInfo(name = "treze") var treze: Boolean,
    @ColumnInfo(name = "trezeQuinze") var trezeQuinze: Boolean,
    @ColumnInfo(name = "trezeTrinta") var trezeTrinta: Boolean,
    @ColumnInfo(name = "trezeQuarentaCinco") var trezeQuarentaCinco: Boolean,

    @ColumnInfo(name = "quatorze") var quatorze: Boolean,
    @ColumnInfo(name = "quatorzeQuinze") var quatorzeQuinze: Boolean,
    @ColumnInfo(name = "quatorzeTrinta") var quatorzeTrinta: Boolean,
    @ColumnInfo(name = "quatorzeQuarentaCinco") var quatorzeQuarentaCinco: Boolean,

    @ColumnInfo(name = "quinze") var quinze: Boolean,
    @ColumnInfo(name = "quinzeQuinze") var quinzeQuinze: Boolean,
    @ColumnInfo(name = "quinzeTrinta") var quinzeTrinta: Boolean,
    @ColumnInfo(name = "quinzeQuarentaCinco") var quinzeQuarentaCinco: Boolean,

    @ColumnInfo(name = "dezesseis") var dezesseis: Boolean,
    @ColumnInfo(name = "dezesseisQuinze") var dezesseisQuinze: Boolean,
    @ColumnInfo(name = "dezesseisTrinta") var dezesseisTrinta: Boolean,
    @ColumnInfo(name = "dezesseisQuarentaCinco") var dezesseisQuarentaCinco: Boolean,

    @ColumnInfo(name = "dezessete") var dezessete: Boolean,
    @ColumnInfo(name = "dezesseteQuinze") var dezesseteQuinze: Boolean,
    @ColumnInfo(name = "dezesseteTrinta") var dezesseteTrinta: Boolean,
    @ColumnInfo(name = "dezesseteQuarentaCinco") var dezesseteQuarentaCinco: Boolean,

    @ColumnInfo(name = "dezoito") var dezoito: Boolean,
    @ColumnInfo(name = "dezoitoQuinze") var dezoitoQuinze: Boolean,
    @ColumnInfo(name = "dezoitoTrinta") var dezoitoTrinta: Boolean,
    @ColumnInfo(name = "dezoitoQuarentaCinco") var dezoitoQuarentaCinco: Boolean,

    @ColumnInfo(name = "dezenove") var dezenove: Boolean,
    @ColumnInfo(name = "dezenoveQuinze") var dezenoveQuinze: Boolean,
    @ColumnInfo(name = "dezenoveTrinta") var dezenoveTrinta: Boolean,
    @ColumnInfo(name = "dezenoveQuarentaCinco") var dezenoveQuarentaCinco: Boolean,
)