package com.app.gestaoconsulta.Ui

import com.app.gestaoconsulta.Model.CadastroMedico

interface LoadFragment {
    fun openSecondFragment()
    fun cadastroSelected(id: Int)
    fun excluirItem(cad: CadastroMedico)
}