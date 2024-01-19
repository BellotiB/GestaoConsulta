package com.app.gestaoconsulta.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.Model.PedidosAgendamentoHistorico
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.View.Adapter.AdapterPedidosHistorico
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentPedidosAgendamentoHistoricoBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PedidosAgendamentoHistoricoFragment : Fragment() {

    private var _binding: FragmentPedidosAgendamentoHistoricoBinding? = null
    private val binding get() = _binding!!
    private var consultaViewModel : ConsultaViewModel? = null
    private var adapter : AdapterPedidosHistorico?= null
    private val pedidoAgendamentosList = mutableListOf<PedidoAgendamento>()
    private val pedidosHistorico = mutableListOf<PedidosAgendamentoHistorico>()
    private val allMedicos = mutableListOf<CadastroMedico>()
    private val allUsuarios = mutableListOf<Usuarios>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPedidosAgendamentoHistoricoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultaViewModel  = ViewModelProvider(requireActivity())[ConsultaViewModel::class.java]
        loadPedidosAgendamerntoHistorico()
    }

    private fun loadPedidosAgendamerntoHistorico() {
        lifecycleScope.launch {
            consultaViewModel?.allPedidosAtendimento?.collectLatest { pedAgend ->
                pedAgend.forEach {
                    val ped = PedidoAgendamento()
                    ped.idUsuario = it.idUsuario
                    ped.dataSelecionada.idCadastro = it.idMedico
                    ped.dataSelecionada.dataAtendimento = it.dataCadastrada
                    ped.horaSelecionada = it.horaCadastrada

                    pedidoAgendamentosList.add(ped)
                }
                loadMedicos()
            }
        }
    }
    private fun loadMedicos() {
        lifecycleScope.launch {
            consultaViewModel?.allCadastros?.collectLatest {
               it.forEach {
                   val cadMed = CadastroMedico()
                   cadMed.id = it.id
                   cadMed.nome = it.nome
                   cadMed.especialidade = it.especialidade

                   allMedicos.add(cadMed)
               }
                loadUsuarios()
            }
        }
    }
    private fun loadUsuarios() {
        lifecycleScope.launch {
            consultaViewModel?.allUsuarios?.collectLatest {
              it.forEach {
                  val user = Usuarios()
                  user.nome = it.nome
                  user.idUsuario = it.idUsuario

                  allUsuarios.add(user)
              }
                setupListPedidosHistorico()
            }
        }
    }
    private fun setupListPedidosHistorico(){
      pedidoAgendamentosList.forEach {pedidoAgendamento ->
          allUsuarios.forEach { usuarios ->
              if(pedidoAgendamento.idUsuario == usuarios.idUsuario){
                  val ped = PedidosAgendamentoHistorico()
                  ped.data = pedidoAgendamento.dataSelecionada.dataAtendimento
                  ped.horario = pedidoAgendamento.horaSelecionada
                  ped.nome = usuarios.nome
                  pedidosHistorico.add(ped)
              }
          }
          setupRecyclerView()
      }
    }
    private fun setupRecyclerView() {
        binding.rvPedidos.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterPedidosHistorico(pedidosHistorico)
        binding.rvPedidos.adapter = adapter
    }
}