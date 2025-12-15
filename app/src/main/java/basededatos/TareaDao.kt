package com.example.supersexiboys.basededatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface TareaDao {
    // Tareas pendientes (solo completada = 0)
    @Query("SELECT * FROM TareaEntity WHERE completada = 0")
    fun getAll(): List<TareaEntity>

    // Tareas completadas (completada = 1)
    @Query("SELECT * FROM TareaEntity WHERE completada = 1 ORDER BY fecha_terminada DESC")
    fun getCompleted(): List<TareaEntity>

    @Insert
    fun insertAll(vararg tarea: TareaEntity)

    @Update
    fun update(tarea: TareaEntity)

    @Query("DELETE FROM TareaEntity")
    fun deleteAll()

    @Delete
    fun delete(tarea: TareaEntity)
}