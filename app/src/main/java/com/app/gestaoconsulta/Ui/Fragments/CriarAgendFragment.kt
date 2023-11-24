package com.app.gestaoconsulta.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.gestaoconsulta.R
import com.app.gestaoconsulta.databinding.FragmentCriarAgendBinding
import com.app.gestaoconsulta.databinding.FragmentUsuariosBinding


class CriarAgendFragment : Fragment() {

    private var _binding: FragmentCriarAgendBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCriarAgendBinding.inflate(inflater, container, false)
        return binding.root
    }


}