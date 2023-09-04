package com.app.gestaoconsulta.Ui

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private val cadastrosList = mutableListOf<CadastroMedico>()
    private var adapter : AdapterCadastro? = null
    private lateinit var gestureDetector: GestureDetectorCompat
    private lateinit var callBack : LoadFragment
     private val consultaViewModel : ConsultaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeListener()
        loadList()
        salvarLista()
    }

    private fun salvarLista() {
        binding.salvarLista.setOnClickListener {

        }
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
            consultaViewModel?.cadastro?.value?.id = binding.id.text.toString()
            updateList()
            saveCadastro()
            cleanCadastro()
        }
    }

    private fun saveCadastro() {
        consultaViewModel.insertCadastro()
    }

    private fun cleanCadastro() {
        binding.nome.text?.clear()
        binding.especialidade.text?.clear()
    }

    private fun loadList(){
        lifecycleScope.launch {
            consultaViewModel?.cadastroList?.collectLatest { list ->
                cadastrosList.addAll(list)
                setupList()
            }
        }
    }
    private fun setupList(){
        binding.rvCadastros.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterCadastro(cadastrosList,callBack)
        binding.rvCadastros.adapter = adapter
    }
    private fun updateList(){
        lifecycleScope.launch {
            consultaViewModel.cadastro.collectLatest { cad ->
               adapter?.updateCadastroList(cad)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}