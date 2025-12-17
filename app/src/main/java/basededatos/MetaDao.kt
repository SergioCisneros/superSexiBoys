package com.example.supersexiboys.basededatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface MetaDao {

    @Query("SELECT * FROM MetaEntity")
    fun getAll(): List<MetaEntity>

    @Query("SELECT * FROM MetaEntity WHERE completada = 1")
    fun getCompleted(): List<MetaEntity>

    @Insert
    fun insertAll(vararg meta: MetaEntity)

    @Update
    fun update(meta: MetaEntity)

    @Query("DELETE FROM MetaEntity")
    fun deleteAll()

    @Delete
    fun delete(meta: MetaEntity)

    @Query("SELECT * FROM MetaEntity WHERE Id = :id LIMIT 1")
    fun getById(id: Int): MetaEntity?

}

