package com.app.gestaoconsulta.Ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.ViewModel.ConsultaViewModel
import com.app.gestaoconsulta.databinding.ItemPeodidoAgendamentoBinding


class AdapterPedidoAgendamento(
    private val pedidosList: MutableList<PedidoAgendamento>, consultaViewModel: ConsultaViewModel?
):RecyclerView.Adapter<AdapterPedidoAgendamento.PedidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val binding = ItemPeodidoAgendamentoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PedidoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pedidosList.size
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position:Int) {
        val pedido = pedidosList[position]
        holder.bind(pedido)


    }

    fun updatePedidoList(ped: MutableList<PedidoAgendamento>) {
        pedidosList.clear()
        pedidosList.addAll(ped)
        notifyDataSetChanged()
    }

    inner class PedidoViewHolder(private val binding: ItemPeodidoAgendamentoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ped: PedidoAgendamento) {
            binding.tvNomecliente.text = ped.idUsuario
            binding.tvNomemedico.text
            binding.tvStartDate.text = ped.dataSelecionada.dataAtendimento
            binding.tvHoraSelecionada.text = ped.horaSelecionada
        }
    }
}