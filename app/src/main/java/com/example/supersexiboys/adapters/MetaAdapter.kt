package com.example.supersexiboys.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.basededatos.MetaEntity
import com.example.supersexiboys.databinding.ItemMetaBinding

class MetaAdapter(
    private val onClick: (MetaEntity) -> Unit // cuando tocamos el item
) : RecyclerView.Adapter<MetaAdapter.MetaViewHolder>() {

    private val metas = mutableListOf<MetaEntity>()

    inner class MetaViewHolder( // sera cada fila del recycler view
        private val binding: ItemMetaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meta: MetaEntity) { // llenamos al item con la indo del MetEntity
            binding.textoMeta.text = meta.Titulo
            binding.actividades.text = "Actividades: ${meta.Actividades.size}"
            binding.etiqueta.text = meta.Etiqueta

            // muestra procentaje
            binding.textoPorcentaje.text = "${meta.Progreso}%"

            binding.root.setOnClickListener {
                onClick(meta)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetaViewHolder { // creamos la vista de un item
        return MetaViewHolder(
            ItemMetaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MetaViewHolder, position: Int) {
        holder.bind(metas[position])
    }

    override fun getItemCount(): Int = metas.size

    fun addDataCards(lista: List<MetaEntity>) {
        metas.clear() // limpia la lista del adaptador
        metas.addAll(lista) // agrega los nuevos elementos
        notifyDataSetChanged() // se redibuja la lista, con el recyclerview
    }
}
