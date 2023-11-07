package com.app.gestaoconsulta.Data.Daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.gestaoconsulta.Data.Entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertUsuarioList(usuarios: MutableList<UsuarioEntity>)

    @Query("SELECT * FROM Usuario")
    fun getAllUsuarios(): Flow<MutableList<UsuarioEntity>>

   @Delete
    fun delete(id: UsuarioEntity)
    @Update
    fun update(id: UsuarioEntity)
    @Delete
    fun deleteAllUsuarios(usuarios: MutableList<UsuarioEntity>)
}