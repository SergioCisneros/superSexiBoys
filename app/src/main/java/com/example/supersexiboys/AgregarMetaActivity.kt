package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
    private val context: Context = this

    // Lista real de actividades
    private val listaActividades = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAgregarMetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MetaDataBase.getDatabase(this)
        metaDao = db.metaDao()


        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botón "+" para agregar la actividad escrita
        binding.btnAgregarActividad.setOnClickListener {
            val textoActual = binding.actividadNueva.text.toString().trim()
            if (textoActual.isNotEmpty()) {
                listaActividades.add(textoActual) // Guardar en lista
                binding.actividadNueva.text.clear() // Limpiar EditText
            }
        }

        // Botón "Guardar" crea la meta
        binding.btnGuardar.setOnClickListener {
            val titulo = binding.tituloMeta.text.toString().trim()
            val descripcion = binding.descripcionMeta.text.toString().trim()
            val fechaInicio = binding.fechaInMeta.text.toString().trim()
            val fechaLimite = binding.fechaLiMeta.text.toString().trim()
            val etiqueta = binding.etiquetaNueva.text.toString().trim()

            if (titulo.isEmpty()) {
                binding.tituloMeta.error = "Ingrese un título"
                return@setOnClickListener
            }

            // Guardar la actividad que quede escrita
            val textoActual = binding.actividadNueva.text.toString().trim()
            if (textoActual.isNotEmpty()) listaActividades.add(textoActual)

            val meta = MetaEntity(
                Titulo = titulo,
                Descripcion = descripcion,
                FechaInicio = fechaInicio,
                FechaLimite = fechaLimite,
                Etiqueta = etiqueta,
                Actividades = listaActividades.toMutableList()
            )

            // Guardar en DB y volver a MetasActivity
            GlobalScope.launch(Dispatchers.IO) {
                metaDao.insertAll(meta)
                startActivity(Intent(context, MetasActivity::class.java))
                finish()
            }
        }
    }
}
