package com.example.supersexiboys

import android.content.Context
import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.supersexiboys.basededatos.MetaDao
import com.example.supersexiboys.basededatos.MetaDataBase
import com.example.supersexiboys.databinding.ActivityMetasInternoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MetasInternoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMetasInternoBinding
    private lateinit var metaDao: MetaDao
    private val context: Context = this

    companion object {
        const val DATABASE_NAME = "USER_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMetasInternoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            context,
            MetaDataBase::class.java,
            DATABASE_NAME
        ).build()
        metaDao = db.metaDao()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idMeta = intent.getIntExtra("ID_META", -1)

        // Obtener la meta desde la base de datos en background
        GlobalScope.launch(Dispatchers.IO) {
            val meta = metaDao.getById(idMeta) ?: return@launch

            withContext(Dispatchers.Main) {
                // Cargar título y descripción
                binding.tituloMeta.text = meta.Titulo
                binding.descripcionMeta.text = meta.Descripcion

                val totalActividades = meta.Actividades.size
                var completadas = 0

                // LinearLayout donde van los checkboxes
                val layoutCheckBoxes: LinearLayout = binding.scrollActividadesLinear // define este id en tu layout

                // Limpiar por si hay algo previo
                layoutCheckBoxes.removeAllViews()

                // Crear los checkboxes dinámicamente
                meta.Actividades.forEach { actividad ->
                    val checkBox = CheckBox(context)
                    checkBox.text = actividad
                    checkBox.isChecked = meta.Completada // si la meta ya estaba completa
                    checkBox.setTextColor(resources.getColor(android.R.color.white))
                    checkBox.textSize = 18f

                    checkBox.setOnCheckedChangeListener { _, isChecked ->
                        completadas += if (isChecked) 1 else -1
                        val progreso = (completadas * 100) / totalActividades
                        binding.progresoLineal.progress = progreso
                        binding.porcentajeBarra.text = "$progreso%"

                        // Si completado 100%, actualizar DB
                        if (progreso == 100) {
                            GlobalScope.launch(Dispatchers.IO) {
                                meta.Completada = true
                                meta.FechaTerminada = System.currentTimeMillis()
                                metaDao.update(meta)
                            }
                        } else if (meta.Completada) {
                            GlobalScope.launch(Dispatchers.IO) {
                                meta.Completada = false
                                metaDao.update(meta)
                            }
                        }
                    }

                    // Agregar al LinearLayout
                    layoutCheckBoxes.addView(checkBox)
                }
            }
        }
    }
}
