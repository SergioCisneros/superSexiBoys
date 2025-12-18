package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.supersexiboys.adapters.MetaAdapter
import com.example.supersexiboys.basededatos.MetaDao
import com.example.supersexiboys.basededatos.MetaDataBase
import com.example.supersexiboys.basededatos.MetaEntity
import com.example.supersexiboys.databinding.ActivityMetasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import com.google.firebase.auth.FirebaseAuth

class MetasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMetasBinding
    private lateinit var metaDao: MetaDao

    val context: Context =  this


    private val adapterMeta: MetaAdapter by lazy { // adapter meta se usara recien la primera vez
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


        val db = MetaDataBase.getDatabase(context)
        metaDao = db.metaDao()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerMetas.layoutManager = // manejo de recycler views
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.recyclerMetas.adapter = adapterMeta

        binding.btnNuevaMeta.setOnClickListener {
            startActivity(Intent(context, AgregarMetaActivity::class.java))
        }

        binding.btnMetasCompletadas.setOnClickListener {
            startActivity(Intent(context, MetasCompletadasActivity::class.java))
        }

        binding.btnHabitos.setOnClickListener {
            val cambioAHabitos: Intent = Intent(context, HabitosActivity::class.java)
            startActivity(cambioAHabitos)
        }
        binding.btnTareas.setOnClickListener {
            val cambioATareas: Intent = Intent(context, CrearTareaActivity::class.java)
            startActivity(cambioATareas)
        }
        binding.verPerfilButton.setOnClickListener {
            val cambioALogueado: Intent = Intent(context, LogueadoActivity::class.java)
            startActivity(cambioALogueado)
        }

        //Mostrar Imagen de Perfil
        val user = FirebaseAuth.getInstance().currentUser
        val nombreAvatar = user?.photoUrl.toString() // Obtenemos el nombre guardado en Firebase

        when (nombreAvatar) { //Como un Case
            "avatar_uno" -> binding.imagenPerfil.setImageResource(R.drawable.avatar_uno)
            "avatar_dos" -> binding.imagenPerfil.setImageResource(R.drawable.avatar_dos)
            "avatar_tres" -> binding.imagenPerfil.setImageResource(R.drawable.avatar_tres)
            else -> binding.imagenPerfil.setImageResource(R.drawable.perfilvacio)
        }

    }

    // lo llamaremos cada vez que vamos a una pantalla
    override fun onResume() {
        super.onResume()
        cargarMetas()
    }

    private fun cargarMetas() {
        lifecycleScope.launch(Dispatchers.IO) {
            val listaMetas = metaDao.getAll(FirebaseAuth.getInstance().currentUser?.uid ?: "")
                .filter {
                    !it.Completada
                } // filtraremos las metas que no esten completadas

            withContext(Dispatchers.Main) { // nos cambiamos al hilo prinicpal, porque las actualizaciones en la interfaz deben hacerse en el hilo principal
                adapterMeta.addDataCards(listaMetas) // el adaptador del recyclerview de metadapter, recibe la lista de metas
            }
        }
    }
}

