package com.app.gestaoconsulta.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val selectedDates = mutableListOf<Long>()
    private var cadastroSelected = CadastroMedico()
    private var consultaViewModel : ConsultaViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultaViewModel  = ViewModelProvider(requireActivity())[ConsultaViewModel::class.java]
        setupCalendar()
        getCadastroSelected()
        setupViewCadastroSelected()
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
            val dateInMillis = selectedDate.timeInMillis

            if (selectedDates.contains(dateInMillis)) {
                selectedDates.remove(dateInMillis)
            } else {
                selectedDates.add(dateInMillis)
            }

            updateSelectedDatesText()
        }
    }

    private fun updateSelectedDatesText() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val selectedDatesText = selectedDates.joinToString(", ") { dateInMillis ->
            val date = Calendar.getInstance().apply {
                timeInMillis = dateInMillis
            }
            dateFormat.format(date.time)
        }
        binding.textViewSelectedDates.text = selectedDatesText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}