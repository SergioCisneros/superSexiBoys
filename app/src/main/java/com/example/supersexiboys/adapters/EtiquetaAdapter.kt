package com.example.supersexiboys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.basededatos.HabitoDao
import com.example.supersexiboys.databinding.AdapterEtiquetaBinding

class EtiquetaAdapter: RecyclerView.Adapter<EtiquetaAdapter.EtiquetaCardViewHolder>() {
    private val dataCards = mutableListOf<String>()
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtiquetaCardViewHolder {
        context = parent.context
        return EtiquetaCardViewHolder(
            AdapterEtiquetaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EtiquetaCardViewHolder, position: Int) {
        holder.binding(dataCards[position])
    }

    override fun getItemCount(): Int = dataCards.size

    inner class EtiquetaCardViewHolder(private val binding: AdapterEtiquetaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: String) {
            binding.textViewAdapterEtiqueta.text = data
        }
    }

    fun addDataCards(list: List<String>) {
        dataCards.clear()
        dataCards.addAll(list)
    }
}