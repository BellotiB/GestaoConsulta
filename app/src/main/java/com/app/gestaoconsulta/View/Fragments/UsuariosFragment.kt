package com.app.gestaoconsulta.Ui.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.Ui.Adapter.AdapterUsuario
import com.app.gestaoconsulta.Ui.CriarAgendamento
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentUsuariosBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class UsuariosFragment : Fragment() {

    private var _binding: FragmentUsuariosBinding? = null
    private val binding get() = _binding!!
    private val usuarios = mutableListOf<Usuarios>()
    private var adapter : AdapterUsuario? = null
    private lateinit var criarAgend : CriarAgendamento

    private var consultaViewModel : ConsultaViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsuariosBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultaViewModel  = ViewModelProvider(requireActivity())[ConsultaViewModel::class.java]
        loadUsuarios()
        setupRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        criarAgend = context as CriarAgendamento
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
                adapter?.updateUsuarioList(usuarios)
                usuarios.clear()
            }
        }
    }
    private fun setupRecyclerView() {
        binding.rvDatasList.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterUsuario(mutableListOf(),criarAgend)
        binding.rvDatasList.adapter = adapter
    }
}