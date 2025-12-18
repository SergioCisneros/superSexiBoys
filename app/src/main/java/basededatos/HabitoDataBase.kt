package basededatos

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.supersexiboys.basededatos.HabitoDao
import com.example.supersexiboys.basededatos.HabitoEntity

@Database(entities = arrayOf(HabitoEntity::class), version = 3)
abstract class HabitoDataBase: RoomDatabase(){
    abstract fun habitoDao(): HabitoDao
}