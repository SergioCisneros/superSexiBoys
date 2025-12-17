package com.example.supersexiboys

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.supersexiboys.basededatos.MetaDao
import com.example.supersexiboys.basededatos.MetaDataBase
import com.example.supersexiboys.basededatos.MetaEntity
import com.example.supersexiboys.databinding.ActivityAgregarMetaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AgregarMetaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarMetaBinding
    private lateinit var metaDao: MetaDao
    private val listaActividades = mutableListOf<EditText>()
    val context: Context = this



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAgregarMetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            applicationContext,
            MetaDataBase::class.java,
            MetaDataBase.DATABASE_NAME
        ).build()


        metaDao = db.metaDao()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Agregar la primera actividad ya existente en el layout
        listaActividades.add(binding.actividadNueva)

        // Botón para agregar más actividades dinámicamente
        binding.btnAgregarActividad.setOnClickListener {
            val nuevaActividad = EditText(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                hint = "Actividad"
            }
            binding.layoutActividades.addView(nuevaActividad)
            listaActividades.add(nuevaActividad)
        }

        // Botón Guardar
        binding.btnGuardar.setOnClickListener {
            val titulo = binding.tituloMeta.text.toString().trim()
            val descripcion = binding.descripcionMeta.text.toString().trim()
            val fechaInicio = binding.fechaInMeta.text.toString().trim()
            val fechaLimite = binding.fechaLiMeta.text.toString().trim()
            val etiqueta = binding.etiquetaNueva.text.toString().trim()

            // Crear lista de actividades
            val actividadesMeta: MutableList<String> =
                listaActividades
                    .map { it.text.toString().trim() }
                    .filter { it.isNotEmpty() }
                    .toMutableList()


            if (titulo.isEmpty()) {
                binding.tituloMeta.error = "Ingrese un título"
                return@setOnClickListener
            }

            val meta = MetaEntity(
                Titulo = titulo,
                Descripcion = descripcion,
                FechaInicio = fechaInicio,
                FechaLimite = fechaLimite,
                Etiqueta = etiqueta,
                Actividades = actividadesMeta
            )

            // Guardar en la base de datos y volver a MetasActivity
            GlobalScope.launch(Dispatchers.IO) {
                metaDao.insertAll(meta)

                // Volver a MetasActivity
                val intent = Intent(context, MetasActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

