package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_DESCRIPCION
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_ETIQUETAS
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_FRECUENCIA
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_REPETICIONES
import com.example.supersexiboys.AgregandoHabitosActivity.Companion.ID_PASO_TITULO
import com.example.supersexiboys.databinding.ActivityComunidadesBinding
import com.example.supersexiboys.databinding.ActivityHabitosBinding

class ComunidadesActivity : AppCompatActivity() {
    val context: Context = this
    private lateinit var binding: ActivityComunidadesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityComunidadesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonEmpieza.setOnClickListener {
            val listaEtiquetas = mutableListOf<String>()
            val titulo: String = binding.textTitulo.text.toString()
            val descripcion: String = binding.textDescripcion.text.toString()
            val repeticiones: String = binding.textContador.text.toString()
            val frecuencia = "Diario"
            listaEtiquetas.add("UPB")
            listaEtiquetas.add(frecuencia)
            if(titulo.isNotEmpty() && frecuencia.isNotEmpty() && repeticiones.toIntOrNull() != null){
                val cambioAHabitos: Intent = Intent(context, HabitosActivity::class.java)
                cambioAHabitos.apply {
                    putExtra(ID_PASO_TITULO,titulo)
                    putExtra(ID_PASO_DESCRIPCION,descripcion)
                    putExtra(ID_PASO_FRECUENCIA,frecuencia)
                    putExtra(ID_PASO_REPETICIONES,repeticiones)
                    putStringArrayListExtra(ID_PASO_ETIQUETAS, ArrayList(listaEtiquetas))
                }
                startActivity(cambioAHabitos)
            }
        }
    }

}