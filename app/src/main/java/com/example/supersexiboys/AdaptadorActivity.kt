package com.example.supersexiboys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.basededatos.TareaEntity

class TareaAdapter(
    private val listaTareas: List<TareaEntity>,
    private val onTaskCompleted: (TareaEntity) -> Unit
) : RecyclerView.Adapter<TareaAdapter.TareaViewHolder>() {

    class TareaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.text_item_titulo)
        val descripcion: TextView = itemView.findViewById(R.id.text_item_descripcion)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_completar_tarea)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TareaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_adaptador, parent, false)
        return TareaViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TareaViewHolder,
        position: Int
    ) {
        val tarea = listaTareas[position]
        holder.titulo.text = tarea.titulo
        holder.descripcion.text = tarea.descripcion

        if (tarea.completada) {
            holder.checkBox.visibility = View.GONE
        } else {
            holder.checkBox.visibility = View.VISIBLE
            holder.checkBox.setOnCheckedChangeListener(null)
            holder.checkBox.isChecked = false

            holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onTaskCompleted(tarea)
                }
            }
        }
    }

    override fun getItemCount(): Int = listaTareas.size
}
