package com.example.supersexiboys.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.basededatos.MetaEntity
import com.example.supersexiboys.databinding.ItemMetaCompletadaBinding

class MetaCompletadaAdapter(
    private val metas: List<MetaEntity>
) : RecyclerView.Adapter<MetaCompletadaAdapter.MetaViewHolder>() {

    inner class MetaViewHolder(val binding: ItemMetaCompletadaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meta: MetaEntity) {
            binding.tituloMeta.text = meta.Titulo
            binding.descripcionMeta.text = meta.Descripcion
            binding.fechaCreacion.text = meta.FechaInicio
            binding.fechaFinalizacion.text = meta.FechaLimite
            binding.etiqueta.text = meta.Etiqueta
            binding.terminado.text = if (meta.Completada) "Meta completada" else "En progreso"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetaViewHolder {
        val binding = ItemMetaCompletadaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MetaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MetaViewHolder, position: Int) {
        holder.bind(metas[position])
    }

    override fun getItemCount(): Int = metas.size
}
