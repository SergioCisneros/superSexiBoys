package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.adapters.EtiquetaAdapter
import com.example.supersexiboys.databinding.ActivityAgregandoHabitosBinding
import com.example.supersexiboys.databinding.ActivityMainBinding

class AgregandoHabitosActivity : AppCompatActivity() {
    val context: Context = this
    companion object{
        val ID_PASO_TITULO = "ID_ENVIO_TITULO"
        val ID_PASO_DESCRIPCION = "ID_ENVIO_DESCRIPCION"
        val ID_PASO_ETIQUETAS = "ID_ENVIO_ETIQUETAS"
        val ID_PASO_FRECUENCIA = "ID_ENVIO_FRECUENCIA"
        val ID_PASO_REPETICIONES = "ID_ENVIO_REPETICIONES"
    }
    private lateinit var binding: ActivityAgregandoHabitosBinding

    val adapterEtiqueta: EtiquetaAdapter by lazy { EtiquetaAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAgregandoHabitosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listaEtiquetas = mutableListOf<String>()
        var frecuencia: String = ""
        binding.recyclerEtiquetas.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.agregarEtiquetaButton.setOnClickListener {
            val nombreDeEtiqueta: String = binding.nombreDeEtiqueta.text.toString()
            if(nombreDeEtiqueta.isNotEmpty()){
                listaEtiquetas.add(nombreDeEtiqueta)
                binding.nombreDeEtiqueta.setText("")
                adapterEtiqueta.addDataCards(listaEtiquetas)
                binding.recyclerEtiquetas.adapter = adapterEtiqueta
            }
        }

        binding.buttonDiario.setOnClickListener {
            binding.buttonDiario.backgroundTintList = ContextCompat.getColorStateList(this, R.color.azul_oscuro)
            binding.buttonSemanal.backgroundTintList = ContextCompat.getColorStateList(this, R.color.azul_pastel)
            binding.textRepeticiones.text = "Repeticiones por día:"
            frecuencia = "Diario"
        }

        binding.buttonSemanal.setOnClickListener {
            binding.buttonSemanal.backgroundTintList = ContextCompat.getColorStateList(this, R.color.azul_oscuro)
            binding.buttonDiario.backgroundTintList = ContextCompat.getColorStateList(this, R.color.azul_pastel)
            binding.textRepeticiones.text = "Repeticiones por semana:"
            frecuencia = "Semanal"
        }

        binding.guardarHabito.setOnClickListener {
            val titulo: String = binding.editTitulo.text.toString()
            val descripcion: String = binding.editDescripcion.text.toString()
            val repeticiones: String = binding.editRepeticiones.text.toString()
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