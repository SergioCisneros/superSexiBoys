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
import com.google.firebase.auth.FirebaseAuth // Asegúrate de importar esto


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
        // 1. Obtener el ID del usuario actual de Firebase
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid ?: ""

        // 2. Validar que el usuario no esté vacío (opcional pero recomendado)
        if (userId.isEmpty()) {
            return
        }

        // 3. Usar la consulta filtrada por usuario que ya tienes en tu DAO
        val tareasTerminadas: List<TareaEntity> = baseDatos.tareaDao().getCompletedByUser(userId)

        // 4. Configurar el adaptador con la lista filtrada
        val adapter = AdaptadorTareaActivity {}
        adapter.addDataCards(tareasTerminadas)
        binding.recyclerViewTerminadas.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewTerminadas.adapter = adapter
    }


}