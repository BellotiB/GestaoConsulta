package com.app.gestaoconsulta.Ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.gestaoconsulta.Model.CadastroMedico
import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.Model.PedidosAgendamentoHistorico
import com.app.gestaoconsulta.Ui.LoadFragment
import com.app.gestaoconsulta.databinding.ItemCadastroBinding
import com.app.gestaoconsulta.databinding.ItemPedidosHistoricoBinding


class AdapterPedidosHistorico(
    private val pedidosList:MutableList<PedidosAgendamentoHistorico>
):RecyclerView.Adapter<AdapterPedidosHistorico.CadastroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CadastroViewHolder {
        val binding = ItemPedidosHistoricoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CadastroViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pedidosList.size
    }

    override fun onBindViewHolder(holder: CadastroViewHolder, position:Int) {
        val ped = pedidosList[position]

        holder.nome.text = ped.nome
        holder.horario.text = ped.horario
        holder.data.text = ped.data
    }

    fun updateCadastroList(cad: MutableList<PedidosAgendamentoHistorico>) {
        pedidosList.clear()
        pedidosList.addAll(cad)
        notifyDataSetChanged()
    }

    inner class CadastroViewHolder(private val binding: ItemPedidosHistoricoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var nome  = binding.tvNomePaciente
        var data  = binding.tvData
        var horario  = binding.tvHorario
    }
}