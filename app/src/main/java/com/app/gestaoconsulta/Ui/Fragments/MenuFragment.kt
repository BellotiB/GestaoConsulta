package com.app.gestaoconsulta.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openFragments()

    }
    private fun openFragments() {
        openCadastroMedicos()
        openPedidosAgendamento()
        openUsuarios()
    }

    private fun openUsuarios() {
        findNavController().navigate(R.id.action_menuFragment_to_usuariosFragment)

    }

    private fun openPedidosAgendamento() {
        findNavController().navigate(R.id.action_menuFragment_to_pedidosAgendamentoFragment)

    }

    private fun openCadastroMedicos() {
        findNavController().navigate(R.id.action_menuFragment_to_cadastradosFragment)

    }
}