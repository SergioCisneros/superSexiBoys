package com.example.supersexiboys

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supersexiboys.databinding.ActivityMetasBinding

import androidx.room.Room
import com.example.supersexiboys.basededatos.MetaDao
import com.example.supersexiboys.basededatos.MetaDataBase
import com.example.supersexiboys.basededatos.MetaEntity
import com.example.supersexiboys.databinding.ActivityAgregarMetaBinding


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


private val listaActividades = mutableListOf<String>()
private var etiquetaSeleccionada: String? = null


class AgregarMetaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarMetaBinding
    val context: Context = this

    private lateinit var metaDao: MetaDao


    companion object {
        val DATABASE_NAME: String = "USER_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAgregarMetaBinding.inflate(layoutInflater)
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


        binding.btnAgregarActividad.setOnClickListener {
            val texto = binding.actividadNueva.text.toString()
            if (texto.isNotBlank()) {
                listaActividades.add(texto)

                val nuevoEditText = EditText(this)
                nuevoEditText.setText(texto)
                nuevoEditText.isEnabled = false
                nuevoEditText.setTextColor(getColor(android.R.color.white))

                binding.main.addView(nuevoEditText)
                binding.actividadNueva.text.clear()
            }



        }

        binding.btnAgregarEtiqueta.setOnClickListener {
            etiquetaSeleccionada = binding.etiquetaNueva.text.toString()
        }


        binding.btnGuardar.setOnClickListener {
            val meta = MetaEntity(
                Titulo = binding.tituloMeta.text.toString(),
                Descripcion = binding.descripcionMeta.text.toString(),
                FechaInicio = binding.fechaInMeta.text.toString(),
                FechaLimite = binding.fechaLiMeta.text.toString(),
                Etiqueta = etiquetaSeleccionada,
                Actividades = listaActividades
            )

            GlobalScope.launch(Dispatchers.IO) {
                metaDao.insertAll(meta) // <- funciona con tu DAO actual
                finish()
            }

        }
    }


}



    // codigos de referencia
/*
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
}*/
