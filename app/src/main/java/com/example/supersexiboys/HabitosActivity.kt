package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_DESCRIPCION
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_TITULO
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_ETIQUETAS
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_FRECUENCIA
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_REPETICIONES
import com.example.supersexiboys.adapters.EtiquetaAdapter
import com.example.supersexiboys.adapters.HabitoAdapter
import com.example.supersexiboys.databinding.ActivityHabitosBinding

class HabitosActivity : AppCompatActivity() {
    val context: Context = this
    private lateinit var binding: ActivityHabitosBinding
    val adapterHabito: HabitoAdapter by lazy { HabitoAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHabitosBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        binding.recyclerHabitos.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }
}