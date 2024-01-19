package com.app.gestaoconsulta.View.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.View.LoadFragment
import com.app.gestaoconsulta.databinding.ItemCadastroBinding

class AdapterMedicosCadastrados(
    private val cadastrosList: MutableList<CadastroMedico>,
    private val callBack: LoadFragment
)
    :RecyclerView.Adapter<AdapterMedicosCadastrados.CadastroViewHolder>() {

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

    override fun onBindViewHolder(holder: CadastroViewHolder, position:Int) {
        val cadastro = cadastrosList[position]
        holder.bind(cadastro)
        holder.menuCard.visibility = View.GONE

        holder.cv_card.setOnClickListener {
            callBack.openPedidosAgendamento(cadastro)
        }
    }

    fun updateCadastroList() {
        cadastrosList.clear()
    }
    fun filterList(cadastro: CadastroMedico) {
        var cloneList = mutableListOf<CadastroMedico>()
        cadastrosList.forEach {
            if(it.especialidade == cadastro.especialidade){
                cloneList.add(it)
            }
        }
        cadastrosList.clear()
        cadastrosList.addAll(cloneList)
        notifyDataSetChanged()
    }

    inner class CadastroViewHolder(private val binding: ItemCadastroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cadastro: CadastroMedico) {
            binding.tvNome.text = cadastro.nome
            binding.tvEspecialidade.text = cadastro.especialidade
        }
        val menuCard : ImageView = binding.menuCard
        val cv_card : CardView = binding.cvCard
    }
}