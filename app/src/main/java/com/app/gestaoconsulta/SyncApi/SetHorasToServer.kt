package com.app.gestaoconsulta.SyncApi

import com.app.gestaoconsulta.Data.Entities.HoraCadastradaEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetHorasToServer @Inject constructor(
    private val apiService: ApiService
) {
    fun fecthHoraCadastrada(list: MutableList<HoraCadastradaEntity>) {
        val result = runCatching { apiService.setHorasCadastradas(list).execute() }
        result.onSuccess {
            it.isSuccessful
        }
        result.onFailure {
            it.printStackTrace()
        }
    }
}
