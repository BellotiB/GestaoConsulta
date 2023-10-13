package com.app.gestaoconsulta.SyncApi

import com.app.gestaoconsulta.Data.Entities.HoraCadastradaEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SetHorasToServer {
    private lateinit var database: FirebaseDatabase

    fun fecthHoraCadastrada(list: MutableList<HoraCadastradaEntity>){
        database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("horas-cadastradas")

        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    referencia.removeValue { databaseError, _ ->
                        if (databaseError == null) {
                            adicionarNovaLista(referencia, list)
                        } else {
                            println("Erro ao atualizar dados existentes: ${databaseError.message}")
                        }
                    }
                } else {
                    adicionarNovaLista(referencia, list)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("Erro ao verificar o cadastro de médicos: ${databaseError.message}")
            }
        })

    }
    private fun adicionarNovaLista(referencia: DatabaseReference, list: MutableList<HoraCadastradaEntity>) {
        referencia.setValue(list) { databaseError, _ ->
            if (databaseError == null) {
                println("Dados adicionados com sucesso.")
            } else {
                println("Erro ao adicionar nova lista: ${databaseError.message}")
            }
        }
    }
}