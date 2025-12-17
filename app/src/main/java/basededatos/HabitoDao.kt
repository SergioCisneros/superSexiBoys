package com.example.supersexiboys.basededatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface HabitoDao {
    @Query("SELECT * FROM habitoentity")
    fun getAll(): List<HabitoEntity>

    @Insert
    fun insertAll(vararg habitoEntity: HabitoEntity)

    @Delete
    fun delete(habitoEntity: HabitoEntity)
}