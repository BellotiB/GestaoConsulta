package com.app.gestaoconsulta.Ui.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gestaoconsulta.Data.Entities.AgendamentoPorUsuarioEntity
import com.app.gestaoconsulta.Model.AgendamentoPorUsuario
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.Ui.Adapter.AdapterPedidoAgendamento
import com.app.gestaoconsulta.Ui.MainActivity
import com.app.gestaoconsulta.Util.DateFormat
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentPedidosAgendamentoBinding
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class PedidosAgendamentoFragment : Fragment() {

    private var _binding: FragmentPedidosAgendamentoBinding? = null
    private val binding get() = _binding!!
    private var consultaViewModel : ConsultaViewModel? = null
    private var usuarios = mutableListOf<Usuarios>()
    private var pedidoAgendamentosList = mutableListOf<PedidoAgendamento>()
    private val agendPorMedicos = mutableListOf<PedidoAgendamento>()
    private var cadastroSelected = CadastroMedico()
    private var adapter : AdapterPedidoAgendamento? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPedidosAgendamentoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultaViewModel  = ViewModelProvider(requireActivity())[ConsultaViewModel::class.java]
        loadCadastroSelected()
        loadPedidoAgendamento()
        getAllUsuarios()
        openReAgendamento()
        voltarCadastroFragment()
    }

    private fun openReAgendamento() {
        binding.openReagendamento.setOnClickListener {
            findNavController().navigate(R.id.action_pedidosAgendamentoFragment_to_reagendamentoFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadCadastroSelected() {
        lifecycleScope.launch {
         consultaViewModel?.cadastroSelected?.collectLatest {
             cadastroSelected = it
         }
        }
    }
    private fun loadPedidoAgendamento() {
        lifecycleScope.launch {
            consultaViewModel?.pedidosAgendamento?.collectLatest { pedAgend ->
                val data = DateFormat().formatCurrentMonthToString()
                pedidoAgendamentosList = pedAgend.filter {it.dataSelecionada.dataAtendimento.substring(3) < data.substring(3)}
                    .toMutableList()
            }
        }
    }

    private fun getAllUsuarios() {
        lifecycleScope.launch {
            consultaViewModel?.allUsuarios?.collectLatest {users ->
                users.forEach {
                    val usuario = Usuarios()
                    usuario.idUsuario = it.idUsuario
                    usuario.id = it.id
                    usuario.nome = it.nome
                    usuario.email = it.email
                    usuario.telefone = it.telefone
                    usuarios.add(usuario)
                }
                setupRecyclerView()
            }
        }
    }
    private fun setupRecyclerView() {
        binding.rvDatasList.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterPedidoAgendamento(mutableListOf(),usuarios)
        binding.rvDatasList.adapter = adapter
        filtrarAgendamentoMedicosSelecionados()
    }

    private fun filtrarAgendamentoMedicosSelecionados(){
        agendPorMedicos.clear()
        pedidoAgendamentosList.forEach {
            if (it.dataSelecionada.idCadastro == cadastroSelected.id) {
                agendPorMedicos.add(it)
            }
        }
        adapter?.updatePedidoList(agendPorMedicos)
    }
    private fun voltarCadastroFragment(){
        binding.ivVoltar.setOnClickListener {
            findNavController().navigate(R.id.action_pedidosAgendamentoFragment_to_cadastradosFragment)
        }
    }
}