package basededatos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TareaEntity::class), version = 1)
abstract class TareaDataBase: RoomDatabase() {
    abstract fun tareaDao(): TareaDao
}