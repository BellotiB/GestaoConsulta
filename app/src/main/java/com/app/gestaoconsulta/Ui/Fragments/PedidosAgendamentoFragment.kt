package com.app.gestaoconsulta.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gestaoconsulta.Ui.Adapter.AdapterPedidoAgendamento
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentPedidosAgendamentoBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PedidosAgendamentoFragment : Fragment() {

    private var _binding: FragmentPedidosAgendamentoBinding? = null
    private val binding get() = _binding!!
    private var consultaViewModel : ConsultaViewModel? = null
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
        setupRecyclerView()
        loadPedidoAgendamento()
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
        adapter = AdapterPedidoAgendamento(mutableListOf(),consultaViewModel)
        binding.rvDatasList.adapter = adapter
    }
}