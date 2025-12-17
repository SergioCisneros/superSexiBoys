package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import basededatos.HabitoDataBase
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_DESCRIPCION
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_TITULO
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_ETIQUETAS
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_FRECUENCIA
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_REPETICIONES
import com.example.supersexiboys.adapters.HabitoAdapter
import com.example.supersexiboys.basededatos.HabitoDao
import com.example.supersexiboys.basededatos.HabitoEntity
import com.example.supersexiboys.databinding.ActivityHabitosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HabitosActivity : AppCompatActivity() {
    val context: Context = this
    private lateinit var binding: ActivityHabitosBinding

    private lateinit var habitoDao: HabitoDao

    companion object{
        val DATABASE_NAME: String = "HABITO_DATABASE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHabitosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val habitoDataBase = Room.databaseBuilder(
            context, HabitoDataBase::class.java,DATABASE_NAME
        ).allowMainThreadQueries().build()

        habitoDao = habitoDataBase.habitoDao()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.nuevoHabitoButton.setOnClickListener {
            val cambioAAgregarHabito: Intent = Intent(context, AgregandoHabitosActivity::class.java)
            startActivity(cambioAAgregarHabito)
        }


        val tituloRecibido: String? = intent.getStringExtra(ID_PASO_TITULO)
        val descripcionRecibida: String? = intent.getStringExtra(ID_PASO_DESCRIPCION)
        val frecuenciaRecibida: String? = intent.getStringExtra(ID_PASO_FRECUENCIA)
        val repeticionesRecibido: String? = intent.getStringExtra(ID_PASO_REPETICIONES)
        val etiquetasRebida: ArrayList<String>? = intent.getStringArrayListExtra(ID_PASO_ETIQUETAS)

        Log.v("antes de userId","todobien")
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        Log.v("despues de userId","todobien")
        binding.recyclerHabitos.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        Log.v("despues de layoutM","todobien")

        Log.v("despues de listaHabitos","todobien")
        val accionActualiza = { habitoSeleccionado: HabitoEntity ->
            habitoDataBase.habitoDao().update(habitoSeleccionado) //Actualizamos la baseDeDatos
        }
        val adapterHabito = HabitoAdapter(accionActualiza)
        val listaHabitos:List<HabitoEntity> = obtenerDatosEnBaseDeDatos()
        adapterHabito.addDataCards(listaHabitos)
        binding.recyclerHabitos.adapter = adapterHabito

        Log.v("despues de addDatacards","todobien")

        Log.v("despues de adapter","todobien")

        if(!tituloRecibido.isNullOrEmpty() && !frecuenciaRecibida.isNullOrEmpty() &&
            !repeticionesRecibido.isNullOrEmpty() && !etiquetasRebida.isNullOrEmpty()){
            GlobalScope.launch {
                val habito = HabitoEntity(
                    id = 0,
                    usuarioId = userId,
                    titulo = tituloRecibido,
                    descripcion = descripcionRecibida.toString(),
                    frecuencia = frecuenciaRecibida,
                    repeticiones = repeticionesRecibido.toInt(),
                    etiquetas = fromList(etiquetasRebida),
                    vecesHecho = 0
                )
                habitoDao.insertAll(habito)
            }
            val cambioAHabitosActiviy: Intent = Intent(context, HabitosActivity::class.java)
            startActivity(cambioAHabitosActiviy)
        }

        binding.verPerfilButton.setOnClickListener {
            val cambioALogueadoActivity: Intent = Intent(context, LogueadoActivity::class.java)
            startActivity(cambioALogueadoActivity)
        }
        binding.metasButton.setOnClickListener {
            val cambioAMetasActivity: Intent = Intent(context, MetasActivity::class.java)
            startActivity(cambioAMetasActivity)
        }
        binding.tareasButton.setOnClickListener {
            val cambioActivityCrearTareaActivity: Intent = Intent(context, CrearTareaActivity::class.java)
            startActivity(cambioActivityCrearTareaActivity)
        }
    }

    private fun obtenerDatosEnBaseDeDatos():List<HabitoEntity>  {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        var ejemplo: List<HabitoEntity> = listOf()
        runBlocking {
            withContext(Dispatchers.IO){
                ejemplo = habitoDao.getAllByUser(userId)
            }
        }
        return ejemplo
    }
    fun fromList(value: List<String>): String {
        return value.joinToString(",") // convierte la lista a un string separado por comas
    }


}