package com.app.gestaoconsulta.SyncApi

import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GetPedidosAgendamentos {

    private lateinit var database: FirebaseDatabase

    suspend fun fecthAgendamentos(): MutableList<PedidoAgendamento> {
        return suspendCancellableCoroutine { continuation ->
            database = FirebaseDatabase.getInstance()
            val referencia = database.getReference("pedido_agendamento")

            referencia.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val pedidos = mutableListOf<PedidoAgendamento>()
                    for (childSnapshot in dataSnapshot.children) {
                       //converter cada elemento da lista
                        val pedidoList = childSnapshot.getValue(object : GenericTypeIndicator<ArrayList<PedidoAgendamento>>() {}
                        )
                        if (pedidoList != null) {
                            pedidos.addAll(pedidoList)
                        }
                    }
                    continuation.resume(pedidos)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    val errorMessage = "Erro ao verificar horas cadastradas: ${databaseError.message}"
                    continuation.resumeWithException(Exception(errorMessage))
                }
            })
        }
    }
}





