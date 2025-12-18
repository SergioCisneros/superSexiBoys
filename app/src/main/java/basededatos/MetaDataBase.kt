package com.example.supersexiboys.basededatos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MetaEntity::class],
    version = 3, // ⬅️ SUBE LA VERSIÓN
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MetaDataBase : RoomDatabase() {

    abstract fun metaDao(): MetaDao

    companion object {

        const val DATABASE_NAME = "META_DATABASE"

        @Volatile
        private var INSTANCE: MetaDataBase? = null

        fun getDatabase(context: Context): MetaDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MetaDataBase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

