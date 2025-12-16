package com.example.supersexiboys.basededatos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MetaEntity::class], version = 2)
abstract class MetaDataBase : RoomDatabase() {
    abstract fun metaDao(): MetaDao
}