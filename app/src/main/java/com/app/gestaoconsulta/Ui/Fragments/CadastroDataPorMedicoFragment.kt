package com.app.gestaoconsulta.Ui.Fragments

import android.content.Context
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
import com.app.gestaoconsulta.Ui.LoadFragment
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentCadastroDataporMedicoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class CadastroDataPorMedicoFragment : Fragment() {

    private var _binding: FragmentCadastroDataporMedicoBinding? = null
    private val binding get() = _binding!!
    private val selectedDates = mutableListOf<String>()
    private var cadastroSelected = CadastroMedico()
    private var datasCadastradas = mutableListOf<DatasCadastradas>()
    private var adapter : AdapterDatasCadastradas? = null
    private val datasCadastradasFlow = MutableStateFlow(datasCadastradas)
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
        setupCalendar()
        getCadastroSelected()
        setupViewCadastroSelected()
        setupRecyclerView()
        setupFlowDatasList()
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
    private fun setupCalendar() {
        val calendarView = binding.calendarView

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            val dateformat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dataSelecionada = dateformat.format(selectedDate.time)

            if (selectedDates.contains(dataSelecionada)) {
                Toast.makeText(requireContext(), "Data já foi selecionada", Toast.LENGTH_SHORT).show()
            } else {
                selectedDates.add(dataSelecionada)
                binding.textViewSelectedDates.text = selectedDates.toString()
                setupDatasCadastradas(dataSelecionada)
            }
        }
    }

    private fun setupDatasCadastradas(dataSelecionada: String) {
        val dateCadastrada = DatasCadastradas()
        dateCadastrada.id = cadastroSelected.id
        dateCadastrada.data = dataSelecionada

        datasCadastradas.add(dateCadastrada)

    }
    private fun setupFlowDatasList() {
        lifecycleScope.launch {
            datasCadastradasFlow.collect { updatedDatasCadastradas ->
                adapter?.updateDatasList(updatedDatasCadastradas)
            }
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