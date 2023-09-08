package com.app.gestaoconsulta.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.DatasCadastradas
import com.app.gestaoconsulta.Ui.Adapter.AdapterDatasCadastradas
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentCadastroDataporMedicoBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class CadastroDataPorMedicoFragment : Fragment() {

    private var _binding: FragmentCadastroDataporMedicoBinding? = null
    private val binding get() = _binding!!
    private var startDate = ""
    private var endDate = ""
    private var horarioInicioAtendimento = ""
    private var horarioUltimoAtendimento = ""
    private var períodoCadaAtendimento = ""
    private var cadastroSelected = CadastroMedico()
    private var datasCadastradas = mutableListOf<DatasCadastradas>()
    private var adapter : AdapterDatasCadastradas? = null
    private var consultaViewModel : ConsultaViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroDataporMedicoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultaViewModel  = ViewModelProvider(requireActivity())[ConsultaViewModel::class.java]
        getCadastroSelected()
        setupViewCadastroSelected()
        setupRecyclerView()
        saveDatasCadastradas()
        getAllDatasCadastradas()

        binding.ivPeriodoAtendimento.setOnClickListener {
            setupDataPicker()
        }
    }

    private fun getAllDatasCadastradas() {
      lifecycleScope.launch {
          consultaViewModel?.allDatasCadastradas?.collect{
              Toast.makeText(requireContext(),"Período Salvo com sucesso",Toast.LENGTH_SHORT).show()
          }
      }
    }

    private fun saveDatasCadastradas() {
        binding.ivSave.setOnClickListener {
            consultaViewModel?.setDatasCadastradas()
        }
    }

    private fun getCadastroSelected() {
        lifecycleScope.launch {
            consultaViewModel?.cadastroSelected?.collectLatest { cad ->
                cadastroSelected = cad
            }
        }
    }
    private fun setupViewCadastroSelected() {
        binding.tvNome.text = cadastroSelected.nome
        binding.tvEspecialidade.text = cadastroSelected.especialidade
    }
    private fun setupDataPicker() {
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Dias de atendimento")
                .setSelection(
                    androidx.core.util.Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                ).build()

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val dateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())

             startDate = dateFormat.format(selection.first)
             endDate = dateFormat.format(selection.second)
            setupDatasCadastradas()
        }
        val pickerInicioAtend =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Horário inicio atendimento")
                .build()

        pickerInicioAtend.addOnPositiveButtonClickListener { horarioSelecionado ->
            val hora = pickerInicioAtend.hour
            val minuto = pickerInicioAtend.minute
             horarioInicioAtendimento = String.format("%02d:%02d", hora, minuto)
        }
        val pickerFinalAtend =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Horário último atendimento")
                .build()

        pickerFinalAtend.addOnPositiveButtonClickListener { horarioSelecionado ->
            val hora = pickerFinalAtend.hour
            val minuto = pickerFinalAtend.minute
             horarioUltimoAtendimento = String.format("%02d:%02d", hora, minuto)

        }
        val pickerPeriodoAtend =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Período de cada atendimento")
                .build()

        pickerPeriodoAtend.addOnPositiveButtonClickListener { horarioSelecionado ->
            val hora = pickerPeriodoAtend.hour
            val minuto = pickerPeriodoAtend.minute
            períodoCadaAtendimento = String.format("%02d:%02d", hora, minuto)
        }

        dateRangePicker.show(childFragmentManager,"DATA_PICKER")
        pickerFinalAtend.show(childFragmentManager,"HOUR_PICKER_FINAL")
        pickerInicioAtend.show(childFragmentManager,"HOUR_PICKER_START")
        pickerPeriodoAtend.show(childFragmentManager,"PERIOD_ATONEMENT")
    }

    private fun setupDatasCadastradas() {
        lifecycleScope.launch {
        val dateCadastrada = DatasCadastradas()
        dateCadastrada.id = cadastroSelected.id
        dateCadastrada.startDate = startDate
        dateCadastrada.endDate = endDate
        dateCadastrada.startHora = horarioInicioAtendimento
        dateCadastrada.endHora = horarioUltimoAtendimento
        dateCadastrada.periodoAtendimento = períodoCadaAtendimento

        datasCadastradas.add(dateCadastrada)
        adapter?.updateDatasList()
        consultaViewModel?.datasCadastradas?.value?.add(dateCadastrada)
        }
    }

    private fun setupRecyclerView() {
        binding.rvDatasList.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterDatasCadastradas(datasCadastradas)
        binding.rvDatasList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}