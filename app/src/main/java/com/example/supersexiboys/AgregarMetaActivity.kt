package com.example.supersexiboys

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supersexiboys.databinding.ActivityMetasBinding

import androidx.room.Room
import com.example.supersexiboys.basededatos.MetaDao
import com.example.supersexiboys.basededatos.MetaDataBase
import com.example.supersexiboys.basededatos.MetaEntity


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class AgregarMetaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMetasBinding
    val context: Context = this


    private lateinit var metaDao: MetaDao


    companion object{
        val NOMBRE_FICHERO_SHARED_PREFERENCES = "Progra3II"
        val NOMBRE_DATO_EJEMPLO = "DatoEjemplo"
        val NOMBRE_ESTUADIANTE_GUARDADO = "EstudianteAlmacenado"
        val DATABASE_NAME: String = "USER_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMetasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ejemploDataBase = Room.databaseBuilder(
            context, MetaDataBase::class.java,
            com.example.supersexiboys.AgregarMetaActivity.Companion.DATABASE_NAME
        ).build()

        metaDao = ejemploDataBase.metaDao()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun guardarDatosEnBaseDeDatos() {
        GlobalScope.launch {
            val meta = MetaEntity(
                id = 0,
                unTextoColumna = "Texto Ejemplo",
                unNumeroColumna = 1,
                unBooleanColumna = true,
            )
            metaDao.insertAll(meta)
        }
    }

    private fun obtenerDatosEnBaseDeDatos():List<MetaEntity>  {
        var ejemplo: List<MetaEntity> = listOf()
        runBlocking {
            withContext(Dispatchers.IO){
                ejemplo = metaDao.getAll()
            }
        }
        return ejemplo
    }
}