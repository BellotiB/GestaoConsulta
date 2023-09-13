package com.app.gestaoconsulta.SyncApi


import com.app.gestaoconsulta.Data.CadastroEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SetCadastroMedicoToServer {
    private lateinit var database: FirebaseDatabase

    fun fecthCadastroMedico(list: MutableList<CadastroEntity>) {
        database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("cadastro-medicos")

        // Verifique se o cadastro de médicos no Firebase está vazio
        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // O cadastro de médicos não está vazio, então apague os dados existentes
                    referencia.removeValue { databaseError, _ ->
                        if (databaseError == null) {
                            // Dados removidos com sucesso, agora adicione a nova lista
                            adicionarNovaLista(referencia, list)
                        } else {
                            // Trate o erro, se houver algum
                            println("Erro ao apagar dados existentes: ${databaseError.message}")
                        }
                    }
                } else {
                    // O cadastro de médicos está vazio, basta adicionar a nova lista
                    adicionarNovaLista(referencia, list)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Trate o erro, se houver algum
                println("Erro ao verificar o cadastro de médicos: ${databaseError.message}")
            }
        })
    }

    private fun adicionarNovaLista(referencia: DatabaseReference, list: MutableList<CadastroEntity>) {
        // Adicione a nova lista ao Firebase
        referencia.setValue(list) { databaseError, _ ->
            if (databaseError == null) {
                println("Dados adicionados com sucesso.")
            } else {
                // Trate o erro, se houver algum
                println("Erro ao adicionar nova lista: ${databaseError.message}")
            }
        }
    }
}