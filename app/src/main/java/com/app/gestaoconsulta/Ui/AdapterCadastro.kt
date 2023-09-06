package com.app.gestaoconsulta.Ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.databinding.ItemCadastroBinding


class AdapterCadastro(private val cadastrosList:MutableList<CadastroMedico>,
                      private val callBack : LoadFragment
):RecyclerView.Adapter<AdapterCadastro.CadastroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CadastroViewHolder {
        val binding = ItemCadastroBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CadastroViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cadastrosList.size
    }

    override fun onBindViewHolder(holder: CadastroViewHolder,position:Int) {
        val cadastro = cadastrosList[position]
        holder.bind(cadastro)
        holder.cardView.setOnClickListener{
            callBack.openSecondFragment()
            callBack.cadastroSelected(cadastro.id)
        }
    }

    fun updateCadastroList(cad: MutableList<CadastroMedico>) {
        cadastrosList.clear()
        cadastrosList.addAll(cad)
        notifyDataSetChanged()
    }

    inner class CadastroViewHolder(private val binding: ItemCadastroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cadastro: CadastroMedico) {
            binding.tvNome.text = cadastro.nome
            binding.tvEspecialidade.text = cadastro.especialidade
        }
        val cardView : CardView = binding.cvCard
    }
}