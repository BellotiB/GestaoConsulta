package com.app.gestaoconsulta.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.gestaoconsulta.databinding.FragmentSecondBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val selectedDates = mutableListOf<Long>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendarView = binding.calendarView

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val dateInMillis = selectedDate.timeInMillis

            if (selectedDates.contains(dateInMillis)) {
                selectedDates.remove(dateInMillis)
            } else {
                selectedDates.add(dateInMillis)
            }

            updateSelectedDatesText()
        }
    }
    private fun updateSelectedDatesText() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val selectedDatesText = selectedDates.joinToString(", ") { dateInMillis ->
            val date = Calendar.getInstance().apply {
                timeInMillis = dateInMillis
            }
            dateFormat.format(date.time)
        }
        binding.textViewSelectedDates.text = selectedDatesText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}