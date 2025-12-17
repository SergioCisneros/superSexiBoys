package com.example.supersexiboys.basededatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface TareaDao {
    //Consultar
    @Query("SELECT * FROM TareaEntity WHERE completada = 0")
    fun getAll(): List<TareaEntity>

    @Query("SELECT * FROM TareaEntity WHERE completada = 1 ORDER BY fecha_terminada DESC")
    fun getCompleted(): List<TareaEntity>

    @Query("SELECT * FROM TareaEntity WHERE completada = 0 AND usuario_id = :userId")
    fun getAllByUser(userId: String): List<TareaEntity>

    @Query("SELECT * FROM TareaEntity WHERE completada = 1 AND usuario_id = :userId ORDER BY fecha_terminada DESC")
    fun getCompletedByUser(userId: String): List<TareaEntity>
    //Insertar
    @Insert
    fun insertAll(vararg tarea: TareaEntity)
    //Actualizar
    @Update
    fun update(tarea: TareaEntity)
    //Eliminar
    @Delete
    fun delete(tarea: TareaEntity)
    @Query("DELETE FROM TareaEntity")
    fun deleteAll()
}