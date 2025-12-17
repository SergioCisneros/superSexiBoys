package com.example.supersexiboys.basededatos

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MetaEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class MetaDataBase : RoomDatabase() {
    abstract fun metaDao(): MetaDao
}
