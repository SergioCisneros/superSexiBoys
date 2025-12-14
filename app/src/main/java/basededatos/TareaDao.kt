package com.example.supersexiboys.basededatos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TareaDao {
    @Query("SELECT * FROM TareaEntity")
    fun getAll(): List<TareaEntity>

    @Insert
    fun insertAll(vararg tarea: TareaEntity)

}