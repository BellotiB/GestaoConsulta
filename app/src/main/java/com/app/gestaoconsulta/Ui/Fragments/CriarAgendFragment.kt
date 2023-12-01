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
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.Ui.Adapter.AdapterCadastro
import com.app.gestaoconsulta.Ui.LoadFragment
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentCriarAgendBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CriarAgendFragment : Fragment() {

    private var _binding: FragmentCriarAgendBinding? = null
    private val binding get() = _binding!!
    private var consultaViewModel : ConsultaViewModel? = null
    private var usuarioSelecioando = Usuarios()
    private val cadastrosList = mutableListOf<CadastroMedico>()
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
    private fun updateList(){
        lifecycleScope.launch {
            consultaViewModel?.allCadastros?.collect {
                it.forEach {
                    val cadastro = CadastroMedico()
                    cadastro.nome = it.nome
                    cadastro.especialidade = it.especialidade
                    cadastro.id = it.id

                    cadastrosList.add(cadastro)
                }
            }
        }
    }
}