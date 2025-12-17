package com.example.supersexiboys.basededatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface MetaDao {
    @Query()
    fun getAll(): List<MetaEntity>

    @Query()
    fun getCompleted(): List<MetaEntity>

    @Insert
    fun insertAll(vararg meta: MetaEntity)

    @Update
    fun update(meta: MetaEntity)

    @Query()
    fun deleteAll()

    @Delete
    fun delete(meta: MetaEntity)
}