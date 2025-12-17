package com.example.supersexiboys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.basededatos.MetaEntity
import com.example.supersexiboys.databinding.ItemMetaBinding

class MetaAdapter(
    private val onClick: (MetaEntity) -> Unit
) : RecyclerView.Adapter<MetaAdapter.MetaCardViewHolder>() {

    private val dataCards = mutableListOf<MetaEntity>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetaCardViewHolder {
        context = parent.context
        return MetaCardViewHolder(
            ItemMetaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MetaCardViewHolder, position: Int) {
        holder.bind(dataCards[position])
    }

    override fun getItemCount(): Int = dataCards.size

    inner class MetaCardViewHolder(
        private val binding: ItemMetaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meta: MetaEntity) {
            binding.textoMeta.text = meta.Titulo
            binding.etiqueta.text = meta.Etiqueta ?: ""
            binding.progresoMeta.progress = 0 // luego lo conectamos

            binding.root.setOnClickListener {
                onClick(meta)
            }
        }
    }

    fun addDataCards(list: List<MetaEntity>) {
        dataCards.clear()
        dataCards.addAll(list)
        notifyDataSetChanged()
    }
}
