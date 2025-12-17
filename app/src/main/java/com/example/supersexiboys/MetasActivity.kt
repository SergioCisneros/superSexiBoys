package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.supersexiboys.adapters.MetaAdapter
import com.example.supersexiboys.basededatos.MetaDao
import com.example.supersexiboys.basededatos.MetaDataBase
import com.example.supersexiboys.basededatos.MetaEntity
import com.example.supersexiboys.databinding.ActivityMetasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MetasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMetasBinding
    private lateinit var metaDao: MetaDao

    private val adapterMeta: MetaAdapter by lazy {
        MetaAdapter { meta ->
            val intent = Intent(this, MetasInternoActivity::class.java)
            intent.putExtra("ID_META", meta.Id)
            startActivity(intent)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMetasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            applicationContext,
            MetaDataBase::class.java,
            MetaDataBase.DATABASE_NAME
        ).build()


        metaDao = db.metaDao()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerMetas.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.recyclerMetas.adapter = adapterMeta

        val listaMetas = obtenerDatosEnBaseDeDatos()
        adapterMeta.addDataCards(listaMetas)

        binding.btnNuevaMeta.setOnClickListener {
            startActivity(Intent(this, AgregarMetaActivity::class.java))
        }

        binding.btnMetasCompletadas.setOnClickListener {
            startActivity(Intent(this, MetasCompletadasActivity::class.java))
        }
    }

    private fun obtenerDatosEnBaseDeDatos(): List<MetaEntity> {
        var lista: List<MetaEntity> = emptyList()
        runBlocking {
            withContext(Dispatchers.IO) {
                lista = metaDao.getAll()
            }
        }
        return lista
    }
}
