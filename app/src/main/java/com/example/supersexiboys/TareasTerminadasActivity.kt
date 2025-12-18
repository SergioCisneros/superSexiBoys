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
import com.google.firebase.auth.FirebaseAuth

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

    // Actu al entrar de nuevo
    override fun onResume() {
        super.onResume()
        mostrarTareasTerminadas()
    }

    private fun mostrarTareasTerminadas() {
        // Obtener el ID del usuario actual de Firebase
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid ?: ""

        // Filtrar DAO por terminadas por el userID
        val tareasTerminadas: List<TareaEntity> = baseDatos.tareaDao().getCompletedByUser(userId)

        //Configurar el adaptador con la lista filtrada
        val adapter = AdaptadorTareaActivity {}
        adapter.addDataCards(tareasTerminadas)
        binding.recyclerViewTerminadas.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewTerminadas.adapter = adapter
    }


}