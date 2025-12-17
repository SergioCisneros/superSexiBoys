package com.example.supersexiboys

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.supersexiboys.basededatos.TareaDataBase
import com.example.supersexiboys.basededatos.TareaEntity
import com.example.supersexiboys.databinding.ActivityTareasTerminadasBinding
import com.example.supersexiboys.adapters.AdaptadorTareaActivity


class TareasTerminadasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTareasTerminadasBinding
    private lateinit var baseDatos: TareaDataBase

    private val context: Context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTareasTerminadasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseDatos = Room.databaseBuilder(
            applicationContext,
            TareaDataBase::class.java,
            "tareas-baseDeDatos"
        ).allowMainThreadQueries().build()

        binding.btnVolver.setOnClickListener {
            finish() //Cierra la actual activity y volvemos a la anterior
        }

        mostrarTareasTerminadas()
    }

    // Si vuelves a entrar, que se actualice
    override fun onResume() {
        super.onResume()
        mostrarTareasTerminadas()
    }

    private fun mostrarTareasTerminadas() {
        val tareasTerminadas: List<TareaEntity> = baseDatos.tareaDao().getCompleted()

        val adapter = AdaptadorTareaActivity { }
        adapter.addDataCards(tareasTerminadas)
        binding.recyclerViewTerminadas.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewTerminadas.adapter = adapter
    }
}