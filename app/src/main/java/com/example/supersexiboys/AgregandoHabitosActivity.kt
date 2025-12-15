package com.example.supersexiboys

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.adapters.EtiquetaAdapter
import com.example.supersexiboys.adapters.HorarioAdapter
import com.example.supersexiboys.databinding.ActivityAgregandoHabitosBinding
import com.example.supersexiboys.databinding.ActivityMainBinding

class AgregandoHabitosActivity : AppCompatActivity() {
    val context: Context = this
    private lateinit var binding: ActivityAgregandoHabitosBinding

    val adapterEtiqueta: EtiquetaAdapter by lazy { EtiquetaAdapter() }
    val adapterHorario: HorarioAdapter by lazy { HorarioAdapter() }
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
        val listaHorarios = mutableListOf<Pair<String,String> >()
        binding.recyclerEtiquetas.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerHorarios.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        binding.agregarEtiquetaButton.setOnClickListener {
            val nombreDeEtiqueta: String = binding.nombreDeEtiqueta.text.toString()
            if(nombreDeEtiqueta.isNotEmpty()){
                listaEtiquetas.add(nombreDeEtiqueta)
                binding.nombreDeEtiqueta.setText("")
                adapterEtiqueta.addDataCards(listaEtiquetas)
                binding.recyclerEtiquetas.adapter = adapterEtiqueta
            }
        }

        binding.agregarHorarioButton.setOnClickListener {
            val diaElegido: String = binding.diaElegido.text.toString()
            val horaElegida: String = binding.horaElegida.text.toString()
            if(diaElegido.isNotEmpty() || horaElegida.isNotEmpty()){
                listaHorarios.add(Pair(diaElegido,horaElegida))
                adapterHorario.addDataCards(listaHorarios)
                binding.recyclerHorarios.adapter = adapterHorario
            }
            binding.horaElegida.setText("")
            binding.diaElegido.setText("")
        }

    }
}