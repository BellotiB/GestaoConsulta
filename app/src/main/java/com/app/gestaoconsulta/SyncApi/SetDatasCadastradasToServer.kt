package com.app.gestaoconsulta.SyncApi

import com.app.gestaoconsulta.Data.Entities.DataCadastradaEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetDatasCadastradasToServer @Inject constructor(
    private val apiService: ApiService
) {
    fun fecthDataCadastrada(list: MutableList<DataCadastradaEntity>){
        val result = runCatching {apiService.setDataCadastrada(list).execute() }

        result.onSuccess { response ->
            response.isSuccessful
        }
        result.onFailure { respnose ->
            respnose.printStackTrace()
        }
    }
}