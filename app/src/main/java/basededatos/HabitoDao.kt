package com.example.supersexiboys.basededatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface HabitoDao {
    @Query("SELECT * FROM habitoentity")
    fun getAll(): List<HabitoEntity>

    @Insert
    fun insertAll(vararg habitoEntity: HabitoEntity)

    @Query("SELECT * FROM HabitoEntity WHERE id = :idBuscado")
    fun getById(idBuscado: Int): HabitoEntity

    @Update
    fun update(habito: HabitoEntity)

    @Query("SELECT * FROM HabitoEntity WHERE usuario_id = :userId")
    fun getAllByUser(userId: String): List<HabitoEntity>
    @Delete
    fun delete(habitoEntity: HabitoEntity)
}