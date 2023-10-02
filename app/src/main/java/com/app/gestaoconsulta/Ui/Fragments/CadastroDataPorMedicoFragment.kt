package com.app.gestaoconsulta.Ui.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.DatasCadastradas
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.Ui.Adapter.AdapterDatasCadastradas
import com.app.gestaoconsulta.Ui.LoadFragment
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentCadastroDataporMedicoBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

@AndroidEntryPoint
class CadastroDataPorMedicoFragment : Fragment() {

    private var _binding: FragmentCadastroDataporMedicoBinding? = null
    private val binding get() = _binding!!
    private var dataAtendimento = ""
    private var cadastroSelected = CadastroMedico()
    private var datasCadastradas = mutableListOf<DatasCadastradas>()
    private var adapter : AdapterDatasCadastradas? = null
    private lateinit var callBack : LoadFragment
    private var consultaViewModel : ConsultaViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroDataporMedicoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack  = context as LoadFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultaViewModel  = ViewModelProvider(requireActivity())[ConsultaViewModel::class.java]
        setupRecyclerView()
        getCadastroSelected()
        getAllDatasCadastradas()
        setupViewCadastroSelected()
        saveDatasCadastradas()

        binding.ivPeriodoAtendimento.setOnClickListener {
            setupDataPicker()
        }
    }

    private fun getAllDatasCadastradas() {
        lifecycleScope.launch {
            consultaViewModel?.allDatasCadastradas?.collect { datesList ->
                datesList.forEach {
                    if(it.idCadastro == cadastroSelected.id){
                        val datas = DatasCadastradas()
                        datas.id = it.id
                        datas.idCadastro = it.idCadastro
                        datas.idDataCadastrada = it.idDataCadastrada
                        datas.dataAtendimento = it.dataAtendimento

                        datasCadastradas.add(datas)
                    }
                }
                adapter?.updateDatasList(datasCadastradas)
                datasCadastradas.clear()
            }
        }
    }

    private fun saveDatasCadastradas() {
        binding.ivSave.setOnClickListener {
            findNavController().navigate(R.id.action_cadastroDataPorMedicoFragment_to_cadastradosFragmentt)
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
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Dia de atendimento")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build()

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val dateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
            val startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            startDateCalendar.timeInMillis = selection
            startDateCalendar.add(Calendar.DAY_OF_MONTH, 1)
            dataAtendimento = dateFormat.format(startDateCalendar.time)

            setupDatasCadastradas()

        }

        dateRangePicker.show(childFragmentManager,"DATA_PICKER")
    }

    private fun setupDatasCadastradas() {
        lifecycleScope.launch {
            val dateCadastrada = DatasCadastradas()
            dateCadastrada.idDataCadastrada = UUID.randomUUID().toString()
            dateCadastrada.idCadastro = cadastroSelected.id
            dateCadastrada.dataAtendimento = dataAtendimento


            datasCadastradas.add(dateCadastrada)
            consultaViewModel?.datasCadastradas?.value?.add(dateCadastrada)
            consultaViewModel?.setDatasCadastradas()
            datasCadastradas.clear()
        }
    }

    private fun setupRecyclerView() {
        binding.rvDatasList.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterDatasCadastradas(mutableListOf(),callBack)
        binding.rvDatasList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}