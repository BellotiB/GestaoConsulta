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
        openPeriodoTarde()
        loadDiasCadastrados()
        salvarHorariosSelecionados()
        selecionarTodosHorarios()
        consultaViewModel?.updateCadastroServer()
        consultaViewModel?.updateDatasCadastradasServer()
        consultaViewModel?.updateHorasCadastradosServer()
    }


    private fun openPeriodoTarde() {
        binding.openTard.setOnClickListener {
            binding.cvManha.visibility = View.GONE
            binding.cvTarde.visibility = View.VISIBLE
        }
        binding.back.setOnClickListener {
            binding.cvManha.visibility = View.VISIBLE
            binding.cvTarde.visibility = View.GONE
        }
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

                        binding.tvOnze.isChecked = it.onze
                        binding.tvOnzeQuinze.isChecked = it.onzeQuinze
                        binding.tvOnzeTrinta.isChecked = it.onzeTrinta
                        binding.tvOnzeQuarentaCinco.isChecked = it.onzeQuarentaCinco

                        binding.tvMeioDia.isChecked = it.doze
                        binding.tvMeioDiaQuinze.isChecked = it.dozeQuinze
                        binding.tvMeioDiaTrinta.isChecked = it.dozeTrinta
                        binding.tvMeioDiaQuarentaCinco.isChecked = it.dozeQuarentaCinco

                        binding.tvTreze.isChecked = it.treze
                        binding.tvTrezeQuinze.isChecked = it.trezeQuinze
                        binding.tvTrezeTrinta.isChecked = it.trezeTrinta
                        binding.tvTrezeQuarentaCinco.isChecked = it.trezeQuarentaCinco

                        binding.tvQuatorze.isChecked = it.quatorze
                        binding.tvQuatorzeQuinze.isChecked = it.quatorzeQuinze
                        binding.tvQuatorzeTrinta.isChecked = it.quatorzeTrinta
                        binding.tvQuatorzeQuarentaCinco.isChecked = it.quatorzeQuarentaCinco

                        binding.tvQuinze.isChecked = it.quinze
                        binding.tvQuinzeQuinze.isChecked = it.quinzeQuinze
                        binding.tvQuinzeTrinta.isChecked = it.quinzeTrinta
                        binding.tvQuinzeQuarentaCinco.isChecked = it.quinzeQuarentaCinco

                        binding.tvDezesseis.isChecked = it.dezesseis
                        binding.tvDezesseisQuinze.isChecked = it.dezesseisQuinze
                        binding.tvDezesseisTrinta.isChecked = it.dezesseisTrinta
                        binding.tvDezesseisQuarentaCinco.isChecked = it.dezesseisQuarentaCinco

                        binding.tvDezesete.isChecked = it.dezessete
                        binding.tvDezesseteQuinze.isChecked = it.dezesseteQuinze
                        binding.tvDezesseteTrinta.isChecked = it.dezesseteTrinta
                        binding.tvDezesseteQuarentaCinco.isChecked = it.dezesseteQuarentaCinco

                        binding.tvDezoito.isChecked = it.dezoito
                        binding.tvDezoitoTrinta.isChecked = it.dezoitoTrinta
                        binding.tvDezoitoQuinze.isChecked = it.dezoitoQuinze
                        binding.tvDezoitoQuarentaCinco.isChecked = it.dezoitoQuarentaCinco

                        binding.tvDezenove.isChecked = it.dezenove
                        binding.tvDezenoveQuinze.isChecked = it.dezenoveQuinze
                        binding.tvDezenoveTrinta.isChecked = it.dezenoveTrinta
                        binding.tvDezenoveQuarentaCinco.isChecked = it.dezenoveQuarentaCinco
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

               horariosCadastrados.doze = binding.tvMeioDia.isChecked
               horariosCadastrados.dozeQuinze = binding.tvMeioDiaQuinze.isChecked
               horariosCadastrados.dozeTrinta = binding.tvMeioDiaTrinta.isChecked
               horariosCadastrados.dozeQuarentaCinco = binding.tvMeioDiaQuarentaCinco.isChecked

               horariosCadastrados.treze = binding.tvTreze.isChecked
               horariosCadastrados.trezeQuinze = binding.tvTrezeQuinze.isChecked
               horariosCadastrados.trezeTrinta = binding.tvTrezeTrinta.isChecked
               horariosCadastrados.trezeQuarentaCinco = binding.tvTrezeQuarentaCinco.isChecked

               horariosCadastrados.quatorze = binding.tvQuatorze.isChecked
               horariosCadastrados.quatorzeQuinze = binding.tvQuatorzeQuinze.isChecked
               horariosCadastrados.quatorzeTrinta = binding.tvQuatorzeTrinta.isChecked
               horariosCadastrados.quatorzeQuarentaCinco = binding.tvQuatorzeQuarentaCinco.isChecked

               horariosCadastrados.quinze = binding.tvQuinze.isChecked
               horariosCadastrados.quinzeQuinze = binding.tvQuinzeQuinze.isChecked
               horariosCadastrados.quinzeTrinta = binding.tvQuinzeTrinta.isChecked
               horariosCadastrados.quinzeQuarentaCinco = binding.tvQuinzeQuarentaCinco.isChecked

               horariosCadastrados.dezesseis = binding.tvDezesseis.isChecked
               horariosCadastrados.dezesseisQuinze = binding.tvDezesseisQuinze.isChecked
               horariosCadastrados.dezesseisTrinta = binding.tvDezesseisTrinta.isChecked
               horariosCadastrados.dezesseisQuarentaCinco = binding.tvDezesseisQuarentaCinco.isChecked

               horariosCadastrados.dezessete = binding.tvDezesete.isChecked
               horariosCadastrados.dezesseteQuinze = binding.tvDezesseteQuinze.isChecked
               horariosCadastrados.dezesseteTrinta = binding.tvDezesseteTrinta.isChecked
               horariosCadastrados.dezesseteQuarentaCinco = binding.tvDezesseteQuarentaCinco.isChecked

               horariosCadastrados.dezoito = binding.tvDezoito.isChecked
               horariosCadastrados.dezoitoQuinze = binding.tvDezoitoQuinze.isChecked
               horariosCadastrados.dezoitoTrinta = binding.tvDezoitoTrinta.isChecked
               horariosCadastrados.dezoitoQuarentaCinco = binding.tvDezoitoQuarentaCinco.isChecked

               horariosCadastrados.dezenove = binding.tvDezenove.isChecked
               horariosCadastrados.dezenoveQuinze = binding.tvDezenoveQuinze.isChecked
               horariosCadastrados.dezenoveTrinta = binding.tvDezenoveTrinta.isChecked
               horariosCadastrados.dezenoveQuarentaCinco = binding.tvDezenoveQuarentaCinco.isChecked

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
        findNavController().navigate(R.id.action_selecionarDiasFragment_to_cadastroDataPorMedicoFragment)
        Toast.makeText(requireContext(),"Horários salvos",Toast.LENGTH_LONG).show()
    }
    private fun selecionarTodosHorarios() {
        binding.todosHorarios.setOnClickListener {
            binding.tvCinco.isChecked = true
            binding.tvCincoQuinze.isChecked = true
            binding.tvCincoTrinta.isChecked = true
            binding.tvCincoQuarentaCinco.isChecked = true

            binding.tvSeis.isChecked = true
            binding.tvSeisQuinze.isChecked = true
            binding.tvSeisTrinta.isChecked = true
            binding.tvSeisQuarentaCinco.isChecked = true

            binding.tvSete.isChecked = true
            binding.tvSeteQuinze.isChecked = true
            binding.tvSeteTrinta.isChecked = true
            binding.tvSeteQuarentaCinco.isChecked = true

            binding.tvOito.isChecked = true
            binding.tvOitoQuinze.isChecked = true
            binding.tvOitoTrinta.isChecked = true
            binding.tvOitoQuarentaCinco.isChecked = true

            binding.tvNove.isChecked = true
            binding.tvNoveQuinze.isChecked = true
            binding.tvNoveTrinta.isChecked = true
            binding.tvNoveQuarentaCinco.isChecked = true

            binding.tvDez.isChecked = true
            binding.tvDezQuinze.isChecked = true
            binding.tvDezTrinta.isChecked = true
            binding.tvDezQuarentaCinco.isChecked = true

            binding.tvOnze.isChecked = true
            binding.tvOnzeQuinze.isChecked = true
            binding.tvOnzeTrinta.isChecked = true
            binding.tvOnzeQuarentaCinco.isChecked = true

            binding.tvOnze.isChecked = true
            binding.tvOnzeQuinze.isChecked = true
            binding.tvOnzeTrinta.isChecked = true
            binding.tvOnzeQuarentaCinco.isChecked = true

            binding.tvMeioDia.isChecked = true
            binding.tvMeioDiaQuinze.isChecked = true
            binding.tvMeioDiaTrinta.isChecked = true
            binding.tvMeioDiaQuarentaCinco.isChecked = true

            binding.tvTreze.isChecked = true
            binding.tvTrezeQuinze.isChecked = true
            binding.tvTrezeTrinta.isChecked = true
            binding.tvTrezeQuarentaCinco.isChecked = true

            binding.tvQuatorze.isChecked = true
            binding.tvQuatorzeQuinze.isChecked = true
            binding.tvQuatorzeTrinta.isChecked = true
            binding.tvQuatorzeQuarentaCinco.isChecked = true

            binding.tvQuinze.isChecked = true
            binding.tvQuinzeQuinze.isChecked = true
            binding.tvQuinzeTrinta.isChecked = true
            binding.tvQuinzeQuarentaCinco.isChecked = true

            binding.tvDezesseis.isChecked = true
            binding.tvDezesseisQuinze.isChecked = true
            binding.tvDezesseisTrinta.isChecked = true
            binding.tvDezesseisQuarentaCinco.isChecked = true

            binding.tvDezesete.isChecked = true
            binding.tvDezesseteQuinze.isChecked = true
            binding.tvDezesseteTrinta.isChecked = true
            binding.tvDezesseteQuarentaCinco.isChecked = true

            binding.tvDezoito.isChecked = true
            binding.tvDezoitoTrinta.isChecked = true
            binding.tvDezoitoQuinze.isChecked = true
            binding.tvDezoitoQuarentaCinco.isChecked = true
            binding.tvDezenove.isChecked = true
            binding.tvDezenoveQuinze.isChecked = true
            binding.tvDezenoveTrinta.isChecked = true
            binding.tvDezenoveQuarentaCinco.isChecked = true
        }
    }
}