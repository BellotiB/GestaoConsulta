package com.app.gestaoconsulta.Ui.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Ui.Adapter.AdapterCadastro
import com.app.gestaoconsulta.Ui.LoadFragment
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentCadastromedicoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class CadastroMedicoFragment : Fragment() {

    private var _binding: FragmentCadastromedicoBinding? = null

    private val binding get() = _binding!!
    private val cadastrosList = mutableListOf<CadastroMedico>()
    private val updateList = mutableListOf<CadastroMedico>()
    private var adapter : AdapterCadastro? = null
    private lateinit var gestureDetector: GestureDetectorCompat
    private lateinit var callBack : LoadFragment

     private var consultaViewModel : ConsultaViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastromedicoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultaViewModel  = ViewModelProvider(requireActivity())[ConsultaViewModel::class.java]
        setupSwipeListener()
        setupList()
        updateList()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack  = context as LoadFragment
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSwipeListener() {
        gestureDetector = GestureDetectorCompat(requireContext(), object : GestureDetector.SimpleOnGestureListener(){
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 != null) {
                    if(e1.y < e2.y){
                        setCadastroListener()
                        return true
                    }
                    return false
                }
                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })
        binding.motionLayout.setOnTouchListener{ _, event ->
            gestureDetector.onTouchEvent(event)
        }
    }
    private fun setCadastroListener() {
        if(binding.nome.text.toString() != "" || binding.especialidade.text.toString() != ""){
            consultaViewModel?.cadastro?.value?.nome = binding.nome.text.toString()
            consultaViewModel?.cadastro?.value?.especialidade = binding.especialidade.text.toString()
            saveCadastro()
            cleanCadastro()
        }
    }

    private fun saveCadastro() {
        consultaViewModel?.insertCadastro()
    }

    private fun cleanCadastro() {
        binding.nome.text?.clear()
        binding.especialidade.text?.clear()
    }

    private fun setupList(){
        binding.rvCadastros.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterCadastro(cadastrosList,callBack)
        binding.rvCadastros.adapter = adapter
    }
    private fun updateList(){
        lifecycleScope.launch {
            consultaViewModel?.allCadastros?.collect {
                it.forEach {
                    val cadastro = CadastroMedico()
                    cadastro.nome = it.nome
                    cadastro.especialidade = it.especialidade
                    cadastro.id = it.id

                    updateList.add(cadastro)
                }
                adapter?.updateCadastroList(updateList)
                consultaViewModel?.cleanCadastroList()
                consultaViewModel?.cadastroList?.value?.addAll(updateList)
                updateList.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}