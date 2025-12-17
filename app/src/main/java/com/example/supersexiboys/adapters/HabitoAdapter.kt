package com.example.supersexiboys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.Habito
import com.example.supersexiboys.databinding.AdapterHabitoBinding

class HabitoAdapter: RecyclerView.Adapter<HabitoAdapter.HabitoCardViewHolder>() {
    private val dataCards = mutableListOf<Habito>()
    private var context: Context? = null
    val adapterEtiqueta: EtiquetaAdapter by lazy { EtiquetaAdapter() }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitoCardViewHolder {
        context = parent.context
        return HabitoCardViewHolder(
            AdapterHabitoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HabitoCardViewHolder, position: Int) {
        holder.binding(dataCards[position])
    }

    override fun getItemCount(): Int = dataCards.size

    inner class HabitoCardViewHolder(private val binding: AdapterHabitoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Habito) {
            binding.textTitulo.text = data.titulo
            binding.textDescripcion.text = data.descripcion
            binding.textContador.text = "0/"+data.repeticiones
            binding.recyclerEtiquetas.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapterEtiqueta.addDataCards(data.etiquetas)
            binding.recyclerEtiquetas.adapter = adapterEtiqueta
        }
    }

    fun addDataCards(list: List<Habito>) {
        dataCards.clear()
        dataCards.addAll(list)
    }
}