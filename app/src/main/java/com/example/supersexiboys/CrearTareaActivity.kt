package com.example.supersexiboys

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.supersexiboys.basededatos.TareaDataBase
import com.example.supersexiboys.basededatos.TareaEntity
import com.example.supersexiboys.databinding.ActivityCrearTareaBinding
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class CrearTareaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrearTareaBinding
    private var countDownTimer: CountDownTimer? = null
    private lateinit var db: TareaDataBase

    private var tituloActual: String? = null
    private var tiempoActual: String? = null
    private var tareaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCrearTareaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            TareaDataBase::class.java,
            "tareas-db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()

        tituloActual = intent.getStringExtra("titulo")
        val descripcion = intent.getStringExtra("descripcion")
        tiempoActual = intent.getStringExtra("tiempo")
        val etiquetas = intent.getStringExtra("etiquetas")
        tareaId = intent.getIntExtra("tareaId", -1)

        binding.textTitulo.text = tituloActual ?: ""
        binding.textDescripcion.text = descripcion ?: ""
        binding.textEtiquetas.text = etiquetas ?: ""

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (tiempoActual != null && tareaId != -1) {
            val partes = tiempoActual!!.split(":")
            if (partes.size == 2) {
                val horas = partes[0].toLongOrNull() ?: 0
                val minutos = partes[1].toLongOrNull() ?: 0
                val totalMillis = (horas * 60 + minutos) * 60 * 1000

                iniciarCronometro(totalMillis)
            }
        }

        binding.buttonCrearOtra.setOnClickListener {
            val intent = Intent(this, TareaActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonBorrarTareas.text = "Ver Tareas Terminadas"
        binding.buttonBorrarTareas.setOnClickListener {
            val intent = Intent(this, TareasTerminadasActivity::class.java)
            startActivity(intent)
        }

        mostrarTareas()
    }

    override fun onResume() {
        super.onResume()
        mostrarTareas()
    }

    private fun iniciarCronometro(milisegundos: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(milisegundos, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val horas = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                val minutos = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val segundos = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                val tiempoFormateado = String.format("%02d:%02d:%02d", horas, minutos, segundos)
                binding.textTiempo.text = "Tiempo restante: $tiempoFormateado"
            }

            override fun onFinish() {
                binding.textTiempo.text = "¡Tarea terminada por tiempo!"
                countDownTimer?.cancel()

                thread {
                    if (tareaId != -1) {
                        val tareasPendientes = db.tareaDao().getAll()
                        val tareaActual = tareasPendientes.find { it.id == tareaId }

                        if (tareaActual != null && !tareaActual.completada) {
                            val tareaCompletada = tareaActual.copy(
                                completada = true,
                                fechaTerminada = System.currentTimeMillis()
                            )
                            db.tareaDao().update(tareaCompletada)

                            runOnUiThread {
                                Toast.makeText(this@CrearTareaActivity, "La tarea '${tareaActual.titulo}' ha terminado su tiempo límite y se marcó como completada.", Toast.LENGTH_LONG).show()
                                mostrarTareas()
                            }
                        }
                    }
                }
            }
        }.start()
    }

    private fun completarTarea(tarea: TareaEntity) {
        thread {
            val tareaCompletada = tarea.copy(
                completada = true,
                fechaTerminada = System.currentTimeMillis()
            )

            db.tareaDao().update(tareaCompletada)

            runOnUiThread {
                Toast.makeText(this, "Tarea '${tarea.titulo}' marcada como terminada.", Toast.LENGTH_SHORT).show()
                mostrarTareas()
                if (tarea.id == tareaId) {
                    countDownTimer?.cancel()
                    binding.textTiempo.text = "Tarea completada."
                }
            }
        }
    }

    private fun mostrarTareas() {
        val todasLasTareas: List<TareaEntity> = db.tareaDao().getAll()

        binding.recyclerViewTareas.layoutManager = LinearLayoutManager(this)

        binding.recyclerViewTareas.adapter = TareaAdapter(todasLasTareas) { tarea ->
            completarTarea(tarea)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
