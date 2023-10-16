package com.app.gestaoconsulta.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.gestaoconsulta.Data.Entities.HoraCadastradaEntity
import com.app.gestaoconsulta.Model.HorariosCadastrados
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel

import com.app.gestaoconsulta.databinding.FragmentSelecionarDiasBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SelecionarDiasFragment : Fragment() {
    private var _binding: FragmentSelecionarDiasBinding? = null
    private val binding get() = _binding!!
    private var consultaViewModel : ConsultaViewModel? = null
    private var idDataCadastrada = ""
    private var idHoraCadastrada = 0
    private var uptade = false
    private var horariosCadastrados = HorariosCadastrados()

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
        loadIdDataCadastro()
        loadDiasCadastrados()
        salvarHorariosSelecionados()
    }
    private fun loadIdDataCadastro(){
        lifecycleScope.launch {
            consultaViewModel?.idDataCadastrada?.collectLatest {
                idDataCadastrada = it
            }
        }
    }

    private fun loadDiasCadastrados() {
        lifecycleScope.launch {
            consultaViewModel?.allHorasCadastradas?.collectLatest { it ->
                it.forEach {
                    if (it.idDataCadastrada == idDataCadastrada) {
                        idHoraCadastrada = it.id
                        uptade = true
                        binding.tvCinco.isChecked = it.cinco
                        binding.tvCincoQuinze.isChecked = it.cincoQuinze
                        binding.tvCincoTrinta.isChecked = it.cincoTrinta
                        binding.tvCincoQuarentaCinco.isChecked = it.cincoQuarentaCinco

                        binding.tvSeis.isChecked = it.seis
                        binding.tvSeisQuinze.isChecked = it.seisQuinze
                        binding.tvSeisTrinta.isChecked = it.seisTrinta
                        binding.tvSeisQuarentaCinco.isChecked = it.seisQuarentaCinco

                        binding.tvSete.isChecked = it.sete
                        binding.tvSeteQuinze.isChecked = it.seteQuinze
                        binding.tvSeteTrinta.isChecked = it.seteTrinta
                        binding.tvSeteQuarentaCinco.isChecked = it.seteQuarentaCinco

                        binding.tvOito.isChecked = it.oito
                        binding.tvSeteQuinze.isChecked = it.oitoQuinze
                        binding.tvSeteTrinta.isChecked = it.oitoTrinta
                        binding.tvSeteQuarentaCinco.isChecked = it.oitoQuarentaCinco

                        binding.tvNove.isChecked = it.nove
                        binding.tvNoveQuinze.isChecked = it.noveQuinze
                        binding.tvNoveTrinta.isChecked = it.noveTrinta
                        binding.tvNoveQuarentaCinco.isChecked = it.noveQuarentaCinco

                        binding.tvDez.isChecked = it.dez
                        binding.tvDezQuinze.isChecked = it.dezQuinze
                        binding.tvDezTrinta.isChecked = it.dezTrinta
                        binding.tvDezQuarentaCinco.isChecked = it.dezQuarentaCinco

                        binding.tvOnze.isChecked = it.onze
                        binding.tvOnzeQuinze.isChecked = it.onzeQuinze
                        binding.tvOnzeTrinta.isChecked = it.onzeTrinta
                        binding.tvOnzeQuarentaCinco.isChecked = it.onzeQuarentaCinco
                    }
                }
            }
        }
    }
    private fun salvarHorariosSelecionados() {
           binding.salvar.setOnClickListener {
               horariosCadastrados.id = idHoraCadastrada
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

               salvarHora()
           }
    }
    private fun salvarHora() {
        if (uptade) {
            consultaViewModel?.updateHoraCadastrada(horariosCadastrados)
        } else {
            consultaViewModel?.insertHoraCadastrada(horariosCadastrados)
        }
        openCadastroMedico()
    }
    private fun openCadastroMedico(){
        findNavController().navigate(R.id.action_selecionarDiasFragment_to_cadastroMedicoFragment)
        Toast.makeText(requireContext(),"Horários salvos",Toast.LENGTH_LONG).show()
    }
}