package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer //Temporizador hacia atras
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager //Para el RECYCLERVIEW
import androidx.room.Room
import com.example.supersexiboys.basededatos.TareaDataBase
import com.example.supersexiboys.basededatos.TareaEntity
import com.example.supersexiboys.databinding.ActivityCrearTareaBinding
import com.example.supersexiboys.adapters.AdaptadorTareaActivity
import com.google.firebase.auth.FirebaseAuth // Importante para filtrar por usuario


class CrearTareaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrearTareaBinding
    private lateinit var baseDeDatos: TareaDataBase
    private val context: Context = this
    private var contador: CountDownTimer? = null //Maneja el temporizador
    private var tituloTarea: String? = null
    private var descripcionTarea: String? = null
    private var tiempoTarea: String? = null
    private var tareaId: Int = -1 // Para identificar tareas nuevas y existentes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCrearTareaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseDeDatos = Room.databaseBuilder(
            applicationContext,
            TareaDataBase::class.java,
            "tareas-baseDeDatos"
        ).allowMainThreadQueries().build()

        tituloTarea = intent.getStringExtra("titulo")
        descripcionTarea = intent.getStringExtra("descripcion")
        tiempoTarea = intent.getStringExtra("tiempo")
        tareaId = intent.getIntExtra("tareaId", -1)

        binding.tituloDeTarea.text = tituloTarea ?: "" //Usamos la elvish function
        binding.descripcionDeTarea.text = descripcionTarea ?: ""

        //Iniciar Temporizador
        if (tiempoTarea != null && tareaId != -1) {
            val partes = tiempoTarea!!.split(":")
            if (partes.size == 2) {
                val horas = partes[0].toIntOrNull() ?: 0
                val minutos = partes[1].toLongOrNull() ?: 0
                val milisegNecesarios = (horas * 60 + minutos) * 60 * 1000
                iniciarCronometro(milisegNecesarios)
            }
        }

        binding.buttonCrearOtraTarea.setOnClickListener {
            val intent = Intent(this, TareaActivity::class.java)
            startActivity(intent)
            finish() //Debemos cerrar CrearTareaActivity
        }

        binding.buttonBorrarTareas.setOnClickListener {
            val intent = Intent(this, TareasTerminadasActivity::class.java)
            startActivity(intent)
        }
        mostrarTareas() // Mostrar lista de tareas en forma de lista
    }

    private fun iniciarCronometro(milisegundos: Long) {
        contador?.cancel() //si hay un contador activo lo matamos

        // crear un temporizador
        contador = object : CountDownTimer(milisegundos, 1000) {

            //cada segundo
            override fun onTick(millisRestantes: Long) {
                val horas = millisRestantes/1000/3600 // mili a hora
                val minutos = (millisRestantes/1000/60) % 60
                val segundos = (millisRestantes/1000) % 60
                val tiempoFormateado = String.format("%02d:%02d:%02d", horas, minutos, segundos)
                binding.tiempoDeTarea.text = "Tiempo restante: $tiempoFormateado"
            }

            //al terminar tiempo
            override fun onFinish() {
                binding.tiempoDeTarea.text = "¡La tarea termino por tiempo!"
                contador?.cancel()

                if(tareaId != -1){
                    // Obtenemos el ID del usuario actual
                    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

                    // Filtramos por usuario para evitar errores de sesión
                    val tareasPendientes = baseDeDatos.tareaDao().getAllByUser(userId)
                    val tareaActual = tareasPendientes.find {it.id == tareaId} //buscamos la tarea que cumpla con el id

                    if(tareaActual != null ) {
                        //marcar la tarea como completada
                        tareaActual.Completada = true
                        tareaActual.FechaTerminada = System.currentTimeMillis()

                        //Cambios de la baseDeDatos
                        baseDeDatos.tareaDao().update(tareaActual)

                        Toast.makeText(context, "La tarea '${tareaActual.Titulo}' ha terminado su tiempo",
                            Toast.LENGTH_SHORT
                        ).show()

                        mostrarTareas()
                    }
                }
            }
        }.start() // Iniciar ejecucion
    }

    // Se ejecuta cada vez que una actividad vuelve a esta pantalla
    // Hace que el recycler view se actualice
    override fun onResume() {
        super.onResume()
        mostrarTareas()
    }

    //Leer tareas, ponerlas en el recycler view y marcar como completada
    private fun mostrarTareas() {
        // Obtenemos el ID del usuario actual para filtrar la lista
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Usamos la función filtrada por usuario
        val listaDeTareas = baseDeDatos.tareaDao().getAllByUser(userId)

        val accionAlTerminar = { tareaSeleccionada: TareaEntity ->

            tareaSeleccionada.Completada = true //terminada
            tareaSeleccionada.FechaTerminada = System.currentTimeMillis()

            baseDeDatos.tareaDao().update(tareaSeleccionada) //Actualizamos la baseDeDatos
            Toast.makeText(context, "¡Tarea completada!", Toast.LENGTH_SHORT).show()

            if (tareaSeleccionada.id == tareaId) {
                binding.tituloDeTarea.text = "Tarea actual terminada"
                binding.tiempoDeTarea.text = "Completado" // Opcional: Cambiar el texto del tiempo

                contador?.cancel()
            }

            mostrarTareas()
        }

        //Configurar el adapter
        binding.recyclerViewTareas.layoutManager = LinearLayoutManager(context)
        val adapter = AdaptadorTareaActivity(accionAlTerminar)
        adapter.addDataCards(listaDeTareas)
        binding.recyclerViewTareas.adapter = adapter
    }

    //Al cerrar la activity detener contador y evitar errores
    override fun onDestroy() {
        super.onDestroy()
        contador?.cancel()
    }
}
