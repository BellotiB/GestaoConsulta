package com.app.gestaoconsulta.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.databinding.FragmentMenuBinding
import com.app.gestaoconsulta.databinding.FragmentUsuariosBinding


class UsuariosFragment : Fragment() {

    private var _binding: FragmentUsuariosBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsuariosBinding.inflate(inflater, container, false)
        return binding.root
    }


}