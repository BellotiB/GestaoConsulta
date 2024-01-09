package com.app.gestaoconsulta.Util

import java.text.SimpleDateFormat
import java.util.Date

class DateFormat {
    fun formatCurrentMonthToString(): String {
        val dataAtual = Date()
        val formato = SimpleDateFormat("MM/yyyy")
        return formato.format(dataAtual)
    }
}