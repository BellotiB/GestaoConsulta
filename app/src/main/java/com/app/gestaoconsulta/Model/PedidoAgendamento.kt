package com.app.gestaoconsulta.Model

import java.io.Serializable

class PedidoAgendamento : Serializable {
    var idUsuario = ""
    var dataSelecionada = DatasCadastradas()
    var horariosDisponiveis = HorariosCadastrados()
    var horaSelecionada = ""
}