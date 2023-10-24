package com.app.gestaoconsulta.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.Ui.Adapter.AdapterPedidoAgendamento
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentPedidosAgendamentoBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PedidosAgendamentoFragment : Fragment() {

    private var _binding: FragmentPedidosAgendamentoBinding? = null
    private val binding get() = _binding!!
    private var consultaViewModel : ConsultaViewModel? = null
    private val usuarios = mutableListOf<Usuarios>()
    private val medicos = mutableListOf<CadastroMedico>()
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
        loadUsuarios()
        loadMedicos()
        setupRecyclerView()
        loadPedidoAgendamento()
    }

    private fun loadMedicos() {
        lifecycleScope.launch {
            consultaViewModel?.allCadastros?.collectLatest {
                it.forEach {
                    val cadastro = CadastroMedico()
                    cadastro.nome = it.nome
                    cadastro.especialidade = it.especialidade
                    cadastro.id = it.id

                    medicos.add(cadastro)
                }
            }
        }
    }

    private fun loadUsuarios() {
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
            }
        }
    }

    private fun loadPedidoAgendamento() {
        lifecycleScope.launch {
            consultaViewModel?.pedidosAgendamento?.collectLatest { pedAgend ->
                adapter?.updatePedidoList(pedAgend)
            }
        }
    }
    private fun setupRecyclerView() {
        binding.rvDatasList.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterPedidoAgendamento(mutableListOf(),usuarios,medicos)
        binding.rvDatasList.adapter = adapter
    }
}