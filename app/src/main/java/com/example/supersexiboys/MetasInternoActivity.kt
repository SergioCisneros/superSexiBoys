package com.example.supersexiboys

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.supersexiboys.adapters.ActividadMetaAdapter
import com.example.supersexiboys.basededatos.MetaDao
import com.example.supersexiboys.basededatos.MetaDataBase
import com.example.supersexiboys.databinding.ActivityMetasInternoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MetasInternoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMetasInternoBinding
    private lateinit var metaDao: MetaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMetasInternoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = MetaDataBase.getDatabase(this)
        metaDao = db.metaDao()


        val idMeta = intent.getIntExtra("ID_META", -1)
        if (idMeta == -1) {
            finish()
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val meta = metaDao.getById(idMeta) ?: return@launch

            withContext(Dispatchers.Main) {
                binding.tituloMeta.text = meta.Titulo
                binding.descripcionMeta.text = meta.Descripcion

                binding.progresoLineal.progress = meta.Progreso
                binding.porcentajeBarra.text = "${meta.Progreso}%"


                val total = meta.Actividades.size
                if (total == 0) return@withContext


                binding.recyclerActividades.layoutManager =
                    LinearLayoutManager(this@MetasInternoActivity)


                //100%
                binding.recyclerActividades.adapter =
                    ActividadMetaAdapter(
                        actividades = meta.Actividades,
                        progresoInicial = meta.Progreso
                    ) { completadas ->

                        val progreso = (completadas * 100) / total
                        binding.progresoLineal.progress = progreso
                        binding.porcentajeBarra.text = "$progreso%"

                        lifecycleScope.launch(Dispatchers.IO) {
                            meta.Progreso = progreso
                            meta.Completada = progreso == 100
                            if (progreso == 100) {
                                meta.FechaTerminada = System.currentTimeMillis()
                            }
                            metaDao.update(meta)
                        }

                        if (progreso == 100) {
                            lifecycleScope.launch(Dispatchers.Main) {
                                Toast.makeText(
                                    this@MetasInternoActivity,
                                    "¡Felicidades! Meta completada",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    }

            }
        }
    }
}
