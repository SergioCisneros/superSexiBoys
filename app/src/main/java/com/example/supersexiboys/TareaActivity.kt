package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.supersexiboys.basededatos.TareaDataBase
import com.example.supersexiboys.basededatos.TareaEntity
import com.example.supersexiboys.databinding.ActivityTareaBinding

class TareaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTareaBinding
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTareaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGuardarEtiqueta.setOnClickListener {

            val titulo = binding.editTitulo.text.toString().trim()
            val descripcion = binding.editDescripcion.text.toString().trim()
            val tiempo = binding.editTiempoLimite.text.toString().trim()
            val etiquetas = "Mis etiquetas"

            if (titulo.isEmpty() || descripcion.isEmpty() || tiempo.isEmpty()) {
                Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación HH:MM
            val partes = tiempo.split(":")
            if (partes.size != 2 || partes[0].toIntOrNull() == null || partes[1].toIntOrNull() !in 0..59) {
                Toast.makeText(context, "Formato incorrecto (HH:MM)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = Room.databaseBuilder(
                applicationContext,
                TareaDataBase::class.java,
                "tareas-db"
            ).allowMainThreadQueries().build()

            val nuevaTarea = TareaEntity(
                titulo = titulo,
                descripcion = descripcion,
                tiempoLimite = tiempo,
                etiquetas = etiquetas
            )

            db.tareaDao().insertAll(nuevaTarea)
            //BUSCAR LA TAREA POR EL ID
            val tareasPendientes = db.tareaDao().getAll()
            val tareaInsertada = tareasPendientes.lastOrNull {
                it.titulo == titulo &&
                        it.descripcion == descripcion &&
                        it.tiempoLimite == tiempo
            }

            val intent = Intent(this, CrearTareaActivity ::class.java)
            intent.putExtra("titulo", titulo)
            intent.putExtra("descripcion", descripcion)
            intent.putExtra("tiempo", tiempo)
            intent.putExtra("etiquetas", etiquetas)

            if (tareaInsertada != null) {
                intent.putExtra("tareaId", tareaInsertada.id)
            }

            startActivity(intent)
        }
    }
}
