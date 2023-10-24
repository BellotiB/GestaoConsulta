package com.app.gestaoconsulta.SyncApi

import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.app.gestaoconsulta.Model.Usuarios
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GetUsuarios {

    private lateinit var database: FirebaseDatabase

    suspend fun fecthUsuarios(): MutableList<Usuarios> {
        return suspendCancellableCoroutine { continuation ->
            database = FirebaseDatabase.getInstance()
            val referencia = database.getReference("usuario")

            referencia.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val usuarios = mutableListOf<Usuarios>()
                    for (childSnapshot in dataSnapshot.children) {
                        val usuario = childSnapshot.getValue(Usuarios::class.java)
                        if (usuario != null) {
                            usuarios.add(usuario)
                        }
                    }
                    continuation.resume((usuarios))
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    val errorMessage = "Erro ao verificar horas cadastradas: ${databaseError.message}"
                    continuation.resumeWithException(Exception(errorMessage))
                }
            })
        }
    }
}





