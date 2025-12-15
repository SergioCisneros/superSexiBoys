package com.example.supersexiboys

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
            "tareas-db"
        ).fallbackToDestructiveMigration() // Agregado para corregir errores de esquema
            .allowMainThreadQueries().build()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mostrarTareasTerminadas()
    }

    override fun onResume() {
        super.onResume()
        mostrarTareasTerminadas()
    }

    private fun mostrarTareasTerminadas() {
        val tareasTerminadas: List<TareaEntity> = baseDatos.tareaDao().getCompleted()

        binding.recyclerViewTerminadas.layoutManager = LinearLayoutManager(this)

        binding.recyclerViewTerminadas.adapter = TareaAdapter(tareasTerminadas) {}
    }
}