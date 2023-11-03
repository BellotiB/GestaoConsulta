import com.app.gestaoconsulta.Model.PedidoAgendamento
import com.google.firebase.database.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RemovePedidosAgendamentos {

    private lateinit var database: FirebaseDatabase

    suspend fun removeAgendamentos(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            database = FirebaseDatabase.getInstance()
            val referencia = database.getReference("pedido_agendamento")

            referencia.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Verifique se há dados no nó "pedido_agendamento"
                    if (dataSnapshot.exists()) {
                        // Remove todos os valores do nó "pedido_agendamento"
                        dataSnapshot.ref.removeValue()
                        continuation.resume(true)
                    } else {
                        // Não há dados para remover
                        continuation.resume(false)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    val errorMessage = "Erro ao remover agendamentos: ${databaseError.message}"
                    continuation.resumeWithException(Exception(errorMessage))
                }
            })
        }
    }
}
