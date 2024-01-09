package com.app.gestaoconsulta.Ui.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.Ui.Adapter.AdapterEspecialidades
import com.app.gestaoconsulta.Ui.Adapter.AdapterMedicosCadastrados
import com.app.gestaoconsulta.Ui.LoadFragment
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentCadastrosBinding
import kotlinx.coroutines.launch

class CadastrosFragment : Fragment() {

    private var _binding: FragmentCadastrosBinding? = null
    private val binding get() = _binding!!
    private var adapter : AdapterMedicosCadastrados? = null
    private var adapterEspecialidades : AdapterEspecialidades? = null
    private val cadastrosList = mutableListOf<CadastroMedico>()
    private val listFiltrada = mutableListOf<CadastroMedico>()
    private var consultaViewModel : ConsultaViewModel? = null
    private lateinit var callBack : LoadFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCadastrosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultaViewModel  = ViewModelProvider(requireActivity())[ConsultaViewModel::class.java]
        setupList()
        openCadastroFragment()
        openFilter()
        closeFiltro()
        backMenu()
        consultaViewModel?.updateCadastroServer()
        consultaViewModel?.updateDatasCadastradasServer()
        consultaViewModel?.updateHorasCadastradosServer()
    }

    private fun backMenu() {
        binding.icVoltar.setOnClickListener {
            findNavController().navigate(R.id.action_cadastradosFragment_to_menuFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack  = context as LoadFragment
    }

    private fun openFilter() {
     binding.openFilter.setOnClickListener {
         binding.cardEspecialidades.visibility = View.VISIBLE
     }
    }

    private fun setupListEspecialidades() {
        cadastrosList.forEach { cad ->
            val idAlreadyExists = listFiltrada.any { it.especialidade == cad.especialidade }
            if (!idAlreadyExists) {
                listFiltrada.add(cad)
            }
        }
        binding.rvEspecialidades.layoutManager = LinearLayoutManager(requireContext())
        adapterEspecialidades = AdapterEspecialidades(listFiltrada,adapter)
        binding.rvEspecialidades.adapter = adapterEspecialidades
    }

    private fun openCadastroFragment() {
        binding.btAdd.setOnClickListener {
            findNavController().navigate(R.id.action_cadastradosFragment_to_cadastroMedicoFragment)
        }
    }

    private fun setupList() {
        lifecycleScope.launch {
            adapter?.updateCadastroList()
            consultaViewModel?.allCadastros?.collect {
                it.forEach {
                    val cadastro = CadastroMedico()
                    cadastro.nome = it.nome
                    cadastro.especialidade = it.especialidade
                    cadastro.id = it.id

                    cadastrosList.add(cadastro)
                }
                binding.rvCadastrados.layoutManager = LinearLayoutManager(requireContext())
                adapter = AdapterMedicosCadastrados(cadastrosList,callBack)
                binding.rvCadastrados.adapter = adapter
                setupListEspecialidades()
            }
        }
    }
    private fun closeFiltro(){
        binding.cardClose.setOnClickListener {
            binding.cardEspecialidades.visibility = View.GONE
        }
    }
}