package com.example.supersexiboys.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.databinding.ItemActividadMetaBinding

class ActividadMetaAdapter(
    private val actividades: List<String>,
    progresoInicial: Int,
    private val onCheckChanged: (Int) -> Unit
) : RecyclerView.Adapter<ActividadMetaAdapter.ActividadViewHolder>() {

    private val estados: MutableList<Boolean>

    init {
        val total = actividades.size
        val marcadas = (progresoInicial * total) / 100
        estados = MutableList(total) { index -> index < marcadas }
    }




    inner class ActividadViewHolder(val binding: ItemActividadMetaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActividadViewHolder {
        return ActividadViewHolder(
            ItemActividadMetaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActividadViewHolder, position: Int) {

        holder.binding.checkActividad.setOnCheckedChangeListener(null)

        holder.binding.checkActividad.text = actividades[position]
        holder.binding.checkActividad.isChecked = estados[position]

        holder.binding.checkActividad.setOnCheckedChangeListener { _, isChecked ->
            estados[position] = isChecked
            onCheckChanged(estados.count { it })
        }
    }


    override fun getItemCount(): Int = actividades.size
}
