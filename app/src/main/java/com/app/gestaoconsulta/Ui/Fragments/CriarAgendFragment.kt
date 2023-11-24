package com.app.gestaoconsulta.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentCriarAgendBinding
import com.app.gestaoconsulta.databinding.FragmentUsuariosBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CriarAgendFragment : Fragment() {

    private var _binding: FragmentCriarAgendBinding? = null
    private val binding get() = _binding!!
    private var consultaViewModel : ConsultaViewModel? = null
    private var usuarioSelecioando = Usuarios()


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
    }

    private fun loadUsuarioSelecionado() {
        lifecycleScope.launch {
            consultaViewModel?.usuarioSelecionado?.collectLatest {
                usuarioSelecioando = it
                binding.nomeUser.text = it.nome
            }
        }
    }
}