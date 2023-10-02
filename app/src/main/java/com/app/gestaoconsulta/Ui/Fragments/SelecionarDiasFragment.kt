package com.app.gestaoconsulta.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.gestaoconsulta.Model.HorariosCadastrados
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel

import com.app.gestaoconsulta.databinding.FragmentSelecionarDiasBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SelecionarDiasFragment : Fragment() {
    private var _binding: FragmentSelecionarDiasBinding? = null
    private val binding get() = _binding!!
    private var consultaViewModel : ConsultaViewModel? = null
    private var idDataCadastrada = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSelecionarDiasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultaViewModel = ViewModelProvider(requireActivity())[ConsultaViewModel::class.java]
        salvarHorariosSelecionados()
        loadIdDataCadastro()
    }

    private fun loadIdDataCadastro(){
        lifecycleScope.launch {
            consultaViewModel?.idDataCadastrada?.collectLatest {
                idDataCadastrada = it
            }
        }
    }
    private fun salvarHorariosSelecionados() {
        val horariosCadastrados = HorariosCadastrados()
        binding.salvar.setOnClickListener {
            horariosCadastrados.idDataCadastrada = idDataCadastrada

            horariosCadastrados.cinco = binding.tvCinco.isChecked
            horariosCadastrados.cincoQuinze = binding.tvCincoQuinze.isChecked
            horariosCadastrados.cincoTrinta = binding.tvCincoTrinta.isChecked
            horariosCadastrados.cincoQuarentaCinco = binding.tvCincoQuarentaCinco.isChecked

            horariosCadastrados.seis = binding.tvSeis.isChecked
            horariosCadastrados.seisQuinze = binding.tvSeisQuinze.isChecked
            horariosCadastrados.seisTrinta = binding.tvSeisTrinta.isChecked
            horariosCadastrados.seisQuarentaCinco = binding.tvSeisQuarentaCinco.isChecked

            horariosCadastrados.sete = binding.tvSete.isChecked
            horariosCadastrados.seteQuinze = binding.tvSeteQuinze.isChecked
            horariosCadastrados.seteTrinta = binding.tvSeteTrinta.isChecked
            horariosCadastrados.seteQuarentaCinco = binding.tvSeteQuarentaCinco.isChecked

            horariosCadastrados.oito = binding.tvOito.isChecked
            horariosCadastrados.oitoQuinze = binding.tvSeteQuinze.isChecked
            horariosCadastrados.oitoTrinta = binding.tvSeteTrinta.isChecked
            horariosCadastrados.oitoQuarentaCinco = binding.tvSeteQuarentaCinco.isChecked

            horariosCadastrados.nove = binding.tvNove.isChecked
            horariosCadastrados.noveQuinze = binding.tvNoveQuinze.isChecked
            horariosCadastrados.noveTrinta = binding.tvNoveTrinta.isChecked
            horariosCadastrados.noveQuarentaCinco = binding.tvNoveQuarentaCinco.isChecked

            horariosCadastrados.dez = binding.tvDez.isChecked
            horariosCadastrados.dezQuinze = binding.tvDezQuinze.isChecked
            horariosCadastrados.dezTrinta = binding.tvDezTrinta.isChecked
            horariosCadastrados.dezQuarentaCinco = binding.tvDezQuarentaCinco.isChecked

            horariosCadastrados.onze = binding.tvOnze.isChecked
            horariosCadastrados.onzeQuinze = binding.tvOnzeQuinze.isChecked
            horariosCadastrados.onzeTrinta = binding.tvOnzeTrinta.isChecked
            horariosCadastrados.onzeQuarentaCinco = binding.tvOnzeQuarentaCinco.isChecked

        }
    }
}