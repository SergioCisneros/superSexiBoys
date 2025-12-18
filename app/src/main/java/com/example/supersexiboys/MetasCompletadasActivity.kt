package com.example.supersexiboys

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.supersexiboys.adapters.MetaCompletadaAdapter
import com.example.supersexiboys.basededatos.MetaDao
import com.example.supersexiboys.basededatos.MetaDataBase
import com.example.supersexiboys.databinding.ActivityMetasCompletadasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MetasCompletadasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMetasCompletadasBinding
    private lateinit var metaDao: MetaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMetasCompletadasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MetaDataBase.getDatabase(this)
        metaDao = db.metaDao()


        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener metas completadas
        val metasCompletadas = runBlocking {
            withContext(Dispatchers.IO) {
                metaDao.getCompleted()
            }
        }

        // Configurar RecyclerView
        binding.recyclerMetasCompletadas.layoutManager = LinearLayoutManager(this)
        binding.recyclerMetasCompletadas.adapter = MetaCompletadaAdapter(metasCompletadas)
    }
}

