package com.app.gestaoconsulta.Ui

import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.DatasCadastradas

interface LoadFragment {
    fun openSecondFragment()
    fun cadastroSelected(id: Int)
    fun excluirItem(cad: CadastroMedico)
    fun excluirDataSelected(dat: DatasCadastradas)
    fun openSelecionarDiasFragment(idDataCadastrada: String)
}