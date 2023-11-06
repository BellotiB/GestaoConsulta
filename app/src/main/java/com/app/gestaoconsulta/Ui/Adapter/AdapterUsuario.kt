package com.app.gestaoconsulta.Ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.gestaoconsulta.Model.Usuarios
import com.app.gestaoconsulta.databinding.ItemUsuariosBinding


class AdapterUsuario(private val usuariosList:MutableList<Usuarios>,

):RecyclerView.Adapter<AdapterUsuario.UsuarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuariosBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsuarioViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usuariosList.size
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position:Int) {
        val usuario = usuariosList[position]
        holder.bind(usuario)
    }

    fun updateUsuarioList(user: MutableList<Usuarios>) {
        usuariosList.clear()
        usuariosList.addAll(user)
        notifyDataSetChanged()
    }

    inner class UsuarioViewHolder(private val binding: ItemUsuariosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cadastro: Usuarios) {
            binding.tvNome.text = cadastro.nome
            binding.tvEmail.text = cadastro.email
            binding.tvTelefone.text = cadastro.telefone
            binding.tvCpf.text = cadastro.cpf

        }
    }
}