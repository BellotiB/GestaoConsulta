package com.app.gestaoconsulta.View.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.gestaoconsulta.DataBase.Entities.AgendamentoPorUsuarioEntity
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.View.LoadFragment
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentCriarAgendBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class CriarAgendFragment : Fragment() {

    private var _binding: FragmentCriarAgendBinding? = null
    private val binding get() = _binding!!
    private var consultaViewModel : ConsultaViewModel? = null
    private var usuarioSelecioando = Usuarios()
    private val cadastrosList = mutableListOf<CadastroMedico>()
    private var dataAtendimento = ""
    private var horaAtendimento = ""
    private lateinit var callBack : LoadFragment



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCriarAgendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultaViewModel  = ViewModelProvider(requireActivity())[ConsultaViewModel::class.java]
        loadUsuarioSelecionado()
        configureView()
        configureDate()
        configureHour()
        loadMedico()
        salvarPedidoAgendamento()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.tvNome.text = ""
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack  = context as LoadFragment
    }

    private fun loadUsuarioSelecionado() {
        lifecycleScope.launch {
            consultaViewModel?.usuarioSelecionado?.collectLatest {
                usuarioSelecioando = it
            }
        }
    }
    private fun configureView() {
        binding.tvNome.text = usuarioSelecioando.nome
    }
    private fun configureDate() {
        binding.inputData.setOnClickListener {
            setupDataPicker()
        }
    }
    private fun configureHour() {
        binding.tvHorario.setOnClickListener {
            setupHorario()
        }
    }
        private fun loadMedico(){
            lifecycleScope.launch {
                consultaViewModel?.allCadastros?.collect {
                    it.forEach {
                        val cadastro = CadastroMedico()
                        cadastro.nome = it.nome
                        cadastro.especialidade = it.especialidade
                        cadastro.id = it.id

                        cadastrosList.add(cadastro)
                    }
                    configureDropDown()
                }
            }
    }
    private fun configureDropDown() {
        val nomesMedicos = cadastrosList.map { it.nome }
        (binding.dropdownMenu.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(nomesMedicos.toTypedArray())
    }

    private fun setupDataPicker() {
        val dateRangePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Dia do atendimento")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build()

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val startDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            startDateCalendar.timeInMillis = selection
            startDateCalendar.add(Calendar.DAY_OF_MONTH, 1)
            dataAtendimento = dateFormat.format(startDateCalendar.time)

            binding.inputData.text = dataAtendimento
        }

        dateRangePicker.show(childFragmentManager,"DATA_PICKER")
    }

    private fun setupHorario(){
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(10)
                .setTitleText("Select Appointment time")
                .build()

        picker.addOnPositiveButtonClickListener {
            horaAtendimento = "${picker.hour} : ${picker.minute}"
            binding.tvHorario.text = horaAtendimento
        }
        picker.show(childFragmentManager,"HORA_PICKER")
    }
    private fun salvarPedidoAgendamento() {
        binding.salvarAgendamento.setOnClickListener {
            val agend = AgendamentoPorUsuarioEntity(
                idUsuario = usuarioSelecioando.idUsuario,
                nomeMedico =  binding.dropdownMenu.editText?.text.toString(),
                horaSelecionada = horaAtendimento,
                dataSelecionada = dataAtendimento
            )
            lifecycleScope.launch(Dispatchers.IO) {
                consultaViewModel?.setPedidoAgendamentoPorUsuario(agend)
            }
            Toast.makeText(requireContext(),"Agendamento Por usuário salvo com sucesso",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.criarAgendamento_to_menuFragment)
        }
    }
}