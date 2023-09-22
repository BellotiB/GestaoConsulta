package com.app.gestaoconsulta.Ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.gestaoconsulta.Model.DatasCadastradas
import com.app.gestaoconsulta.Ui.LoadFragment
import com.app.gestaoconsulta.databinding.ItemDataCadastradaBinding


class AdapterDatasCadastradas(private val datasList:MutableList<DatasCadastradas>,
                              private val callBack : LoadFragment
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
        holder.menuCard.setOnClickListener {
            callBack.excluirDataSelected(data)
            notifyDataSetChanged()
        }
        holder.bind(data)
    }

    fun updateDatasList(datasCadastradas: MutableList<DatasCadastradas>) {
        datasList.clear()
        datasList.addAll(datasCadastradas)
        notifyDataSetChanged()
    }

    inner class CadastroViewHolder(private val binding: ItemDataCadastradaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataCad: DatasCadastradas) {
            binding.tvStartDate.text = dataCad.startDate
            binding.tvHora.text = dataCad.startHora +" "+ dataCad.endHora
            binding.tvPeriodoAtend.text = dataCad.periodoAtendimento
        }
        val menuCard : ImageView = binding.menuCard
    }
}