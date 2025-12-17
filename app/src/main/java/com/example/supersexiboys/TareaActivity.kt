package com.example.supersexiboys

import android.content.Context // Necesario para los intents
import android.content.Intent // Comunicacion
import android.os.Bundle // Guardar datos
import android.widget.Toast // Mensajes de notificacion
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room // Room para SQLiTE
import com.example.supersexiboys.basededatos.TareaDataBase // DB
import com.example.supersexiboys.basededatos.TareaEntity // Tabla
import com.example.supersexiboys.databinding.ActivityTareaBinding // binding

class TareaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTareaBinding
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTareaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGuardarTarea.setOnClickListener {

            val textTitulo: String = binding.editTitulo.text.toString().trim()
            val textDescripcion: String = binding.editDescripcion.text.toString().trim()
            val textTiempoLimit = binding.editTiempoLimite.text.toString().trim()
            val textEtiquetas: String = "Mis etiquetas"

            //Si faltan datos
            if (textTitulo.isEmpty() || textDescripcion.isEmpty() || textTiempoLimit.isEmpty()) {
                Toast.makeText(context, "Debes poner todos los datos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener //Se detiene la ejecucion si faltan datos
            }

            // validar (horas y minutos)
            val partes = textTiempoLimit.split(":") // 10:30 -> (10, 30)
            if (partes.size != 2 || partes[0].toIntOrNull() == null || partes[1].toIntOrNull() !in 0..59) {
                Toast.makeText(context, "Formato incorrecto (Horas:Minutos)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Mensaje de error al poner mal el tiempo
            }

            //Creacion de la base de datos
            val baseDeDatos = Room.databaseBuilder(
                applicationContext, //context global de la DB
                TareaDataBase::class.java, //Clase de la DB Define DAO y TABLAS
                "tareas-baseDeDatos"
            ).allowMainThreadQueries().build() //consultas y construcion

            // Objeto Tarea
            var nuevaTarea = TareaEntity(
                Titulo = textTitulo,
                Descripcion = textDescripcion,
                TiempoLimite = textTiempoLimit,
                Etiquetas = textEtiquetas
            )
            baseDeDatos.tareaDao().insertAll(nuevaTarea) //Se pasan los datos al Dao para guardarlos
            val tareasPendientes = baseDeDatos.tareaDao().getAll() //Lista de tareas

            //Encontrar tareas
            var tareaCreada: TareaEntity? = null

            for(tarea in tareasPendientes){
                if(tarea.Titulo == textTitulo && tarea.Descripcion == textDescripcion && tarea.TiempoLimite == textTiempoLimit){
                    tareaCreada = tarea
                }
            }

            //Intent para CrearTareaActivity
            val intent = Intent(context, CrearTareaActivity ::class.java)
            intent.putExtra("titulo", textTitulo) //clave, valor
            intent.putExtra("descripcion", textDescripcion)
            intent.putExtra("tiempo", textTiempoLimit)
            intent.putExtra("etiquetas", textEtiquetas)

            if (tareaCreada != null) {
                intent.putExtra("tareaId", tareaCreada.id)
            }
            startActivity(intent)
        }
    }
}
