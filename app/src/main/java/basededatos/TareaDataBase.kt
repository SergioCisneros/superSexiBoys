package com.example.supersexiboys.basededatos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TareaEntity::class], version = 3)
abstract class TareaDataBase : RoomDatabase() {
    abstract fun tareaDao(): TareaDao
}