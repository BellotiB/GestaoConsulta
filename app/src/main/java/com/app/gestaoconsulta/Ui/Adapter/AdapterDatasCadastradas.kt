package com.app.gestaoconsulta.Ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.gestaoconsulta.Model.DatasCadastradas
import com.app.gestaoconsulta.databinding.ItemDataCadastradaBinding


class AdapterDatasCadastradas(private val datasList:MutableList<DatasCadastradas>,
):RecyclerView.Adapter<AdapterDatasCadastradas.CadastroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CadastroViewHolder {
        val binding = ItemDataCadastradaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CadastroViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datasList.size
    }

    override fun onBindViewHolder(holder: CadastroViewHolder, position:Int) {
        val data = datasList[position]
        holder.bind(data)
    }

    fun updateDatasList() {
        notifyDataSetChanged()
    }

    inner class CadastroViewHolder(private val binding: ItemDataCadastradaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataCad: DatasCadastradas) {
            binding.tvStartDate.text = dataCad.startDate
            binding.tvEndDate.text = dataCad.endDate
            binding.tvHora.text = dataCad.startHora +" "+ dataCad.endHora
        }
    }
}