package com.app.gestaoconsulta.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.gestaoconsulta.Model.AgendamentoPorUsuario
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentReagendamentoBinding
import com.app.gestaoconsulta.databinding.ItemPeodidoAgendamentoBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReagendamentoFragment : Fragment() {
    private var _binding: FragmentReagendamentoBinding? = null
    private val binding get() = _binding!!
    private var adapter : AdapterReAgendamento? = null
    private var usuarios = mutableListOf<Usuarios>()
    private val pedidoAgendamentosList = mutableListOf<AgendamentoPorUsuario>()
    private val reAgendamentosList = mutableListOf<AgendamentoPorUsuario>()
    private var cadastroSelected = CadastroMedico()
    private var consultaViewModel : ConsultaViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReagendamentoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultaViewModel  = ViewModelProvider(requireActivity())[ConsultaViewModel::class.java]
        loadAgendamentosPorUsuarios()

    }
    private fun loadAgendamentosPorUsuarios() {
        lifecycleScope.launch {
            consultaViewModel?.allAtendimentosPorUsuarios?.collectLatest {
                it.forEach {
                    val agend = AgendamentoPorUsuario()
                    agend.horaSelecionada = it.horaSelecionada
                    agend.dataSelecionada = it.dataSelecionada
                    agend.idUsuario = it.idUsuario
                    agend.nomeMedico = it.nomeMedico
                    pedidoAgendamentosList.add(agend)
                }
                getAllUsuarios()
            }
        }
    }
    private fun getAllUsuarios() {
            lifecycleScope.launch {
                consultaViewModel?.allUsuarios?.collectLatest { users ->
                    users.forEach {
                        val usuario = Usuarios()
                        usuario.idUsuario = it.idUsuario
                        usuario.id = it.id
                        usuario.nome = it.nome
                        usuario.email = it.email
                        usuario.telefone = it.telefone
                        usuarios.add(usuario)
                    }
                    loadCadastroSelected()
                }
            }
        }
    private fun loadCadastroSelected() {
        lifecycleScope.launch {
            consultaViewModel?.cadastroSelected?.collectLatest {
                cadastroSelected = it
                filtrarListaPorUsuarios()
            }
        }
    }
    private fun filtrarListaPorUsuarios(){
        pedidoAgendamentosList.forEach {
            usuarios.forEach { user ->
                if(it.idUsuario == user.idUsuario ){
                    it.nomeUsuario = user.nome
                }
            }
        }
        filtrarListaPorMedicos()
    }
    private fun filtrarListaPorMedicos(){
        pedidoAgendamentosList.forEach {
            if(it.nomeMedico == cadastroSelected.nome){
                reAgendamentosList.add(it)
            }
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvReagendamentoList.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterReAgendamento(reAgendamentosList)
        binding.rvReagendamentoList.adapter = adapter
    }
}

class AdapterReAgendamento(
    private val pedidosList:MutableList<AgendamentoPorUsuario>
):RecyclerView.Adapter<AdapterReAgendamento.ReagendamentoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReagendamentoViewHolder {
        val binding = ItemPeodidoAgendamentoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReagendamentoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pedidosList.size
    }

    override fun onBindViewHolder(holder: ReagendamentoViewHolder, position: Int) {
        val pedidos = pedidosList[position]
        holder.nome.text  = pedidos.nomeUsuario
        holder.data.text = pedidos.dataSelecionada
        holder.hora.text = pedidos.horaSelecionada

    }

    class ReagendamentoViewHolder(private val binding: ItemPeodidoAgendamentoBinding):
        RecyclerView.ViewHolder(binding.root) {
            val nome = binding.tvNomecliente
            val data = binding.tvStartDate
            val hora = binding.tvHoraSelecionada
    }
}
