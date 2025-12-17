package com.example.supersexiboys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.basededatos.MetaEntity
import com.example.supersexiboys.databinding.ItemMetaBinding

class MetaAdapter(
    private val onClick: (MetaEntity) -> Unit
) : RecyclerView.Adapter<MetaAdapter.MetaViewHolder>() {

    private val metas = mutableListOf<MetaEntity>()

    inner class MetaViewHolder(private val binding: ItemMetaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meta: MetaEntity) {
            binding.textoMeta.text = meta.Titulo
            binding.root.setOnClickListener {
                onClick(meta)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetaViewHolder {
        return MetaViewHolder(
            ItemMetaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MetaViewHolder, position: Int) {
        holder.bind(metas[position])
    }

    override fun getItemCount(): Int = metas.size

    fun addDataCards(lista: List<MetaEntity>) {
        metas.clear()
        metas.addAll(lista)
        notifyDataSetChanged()
    }
}
