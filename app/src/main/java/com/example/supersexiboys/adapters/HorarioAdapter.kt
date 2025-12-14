package com.example.supersexiboys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.databinding.AdapterHorarioBinding

class HorarioAdapter: RecyclerView.Adapter<HorarioAdapter.HorarioCardViewHolder>() {
    private val dataCards = mutableListOf<Pair<String,String> >()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorarioCardViewHolder {
        context = parent.context
        return HorarioCardViewHolder(
            AdapterHorarioBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HorarioCardViewHolder, position: Int) {
        holder.binding(dataCards[position])
    }

    override fun getItemCount(): Int = dataCards.size

    inner class HorarioCardViewHolder(private val binding: AdapterHorarioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Pair<String,String>) {
            binding.textViewAdapterHorarioDia.text = data.first
            binding.textViewAdapterHorarioHora.text = data.second
        }
    }

    fun addDataCards(list: List<Pair<String,String> >) {
        dataCards.clear()
        dataCards.addAll(list)
    }
}