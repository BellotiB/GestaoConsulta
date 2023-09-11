package com.app.gestaoconsulta.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.DatasCadastradas
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),LoadFragment {

    private lateinit var binding: ActivityMainBinding

     lateinit var consultaViewModel: ConsultaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        consultaViewModel = ViewModelProvider(this)[ConsultaViewModel::class.java]
    }

    override fun openSecondFragment() {
        findNavController(R.id.nav_host).navigate(R.id.action_cadastroMedicoFragment_to_cadastroDataPorMedicoFragment)
    }

    override fun cadastroSelected(id: Int) {
      consultaViewModel.selectCadastroById(id)
    }

    override fun excluirItem(cad: CadastroMedico) {
        consultaViewModel.deleteCadastro(cad)
    }

    override fun excluirDataSelected(dat: DatasCadastradas) {
        consultaViewModel.deleteDataCadastrada(dat)
    }
}