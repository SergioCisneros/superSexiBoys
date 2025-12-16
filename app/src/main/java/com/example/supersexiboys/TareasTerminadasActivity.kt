package com.example.supersexiboys

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.supersexiboys.basededatos.TareaDataBase
import com.example.supersexiboys.basededatos.TareaEntity
import com.example.supersexiboys.databinding.ActivityTareasTerminadasBinding

class TareasTerminadasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTareasTerminadasBinding
    private lateinit var baseDatos: TareaDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTareasTerminadasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseDatos = Room.databaseBuilder(
            applicationContext,
            TareaDataBase::class.java,
            "tareas-baseDeDatos" // Asegúrate que este nombre sea IGUAL en todas tus activities
        ).allowMainThreadQueries().build()

        binding.btnVolver.setOnClickListener {
            finish() // Esto cierra la pantalla y te regresa a la anterior
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

        binding.recyclerViewTerminadas.layoutManager = LinearLayoutManager(this)

        // Usamos las llaves vacías { } porque aquí no queremos editar nada
        binding.recyclerViewTerminadas.adapter = AdaptadorTareaActivity(tareasTerminadas) { }
    }
}