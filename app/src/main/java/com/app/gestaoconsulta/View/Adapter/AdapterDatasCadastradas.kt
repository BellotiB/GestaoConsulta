package com.app.gestaoconsulta.View.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.gestaoconsulta.Model.DatasCadastradas
import com.app.gestaoconsulta.View.LoadFragment
import com.app.gestaoconsulta.databinding.ItemDataCadastradaBinding


class AdapterDatasCadastradas(
    private val datasList:MutableList<DatasCadastradas>,
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
        holder.card.setOnClickListener{
          callBack.openSelecionarDiasFragment(data.idDataCadastrada)
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
            binding.tvStartDate.text = dataCad.dataAtendimento
        }
        val menuCard : ImageView = binding.menuCard
        val card : CardView = binding.cvCard
    }
}