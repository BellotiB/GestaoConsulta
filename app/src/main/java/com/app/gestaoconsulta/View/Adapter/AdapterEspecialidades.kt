package com.app.gestaoconsulta.View.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.databinding.ItemEspecialidadesBinding

class AdapterEspecialidades(
    private val cadastrosList: MutableList<CadastroMedico>,
    private val adapterMedicosCad: AdapterMedicosCadastrados?,
):RecyclerView.Adapter<AdapterEspecialidades.EspeocialidadeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EspeocialidadeViewHolder {
        val binding = ItemEspecialidadesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EspeocialidadeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cadastrosList.size
    }

    override fun onBindViewHolder(holder: EspeocialidadeViewHolder, position:Int) {
        val cadastro = cadastrosList[position]
        holder.cardView.setOnClickListener {
            adapterMedicosCad?.filterList(cadastro)
        }
        holder.bind(cadastro)
    }

    inner class EspeocialidadeViewHolder(private val binding: ItemEspecialidadesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cadastro: CadastroMedico) {
            binding.tvEspecialidade.text = cadastro.especialidade
        }
        val cardView : CardView = binding.cvCard
    }
}