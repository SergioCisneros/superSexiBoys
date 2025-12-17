package com.example.supersexiboys

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.basededatos.TareaEntity
import com.example.supersexiboys.databinding.ItemTareaBinding
import java.text.SimpleDateFormat // Importante para formatear la fecha
import java.util.Date // Importante para manejar fechas
import java.util.Locale

class AdaptadorTareaActivity(
    private val listaTareas: List<TareaEntity>,
    private val onCheckClick: (TareaEntity) -> Unit
) : RecyclerView.Adapter<AdaptadorTareaActivity.TareaViewHolder>() {

    class TareaViewHolder(val binding: ItemTareaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val binding = ItemTareaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TareaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = listaTareas[position]

        holder.binding.tvTitulo.text = tarea.Titulo
        holder.binding.tvDescripcion.text = tarea.Descripcion

        // --- AQUÍ ESTÁ EL CAMBIO PARA LA FECHA ---
        if (tarea.Completada && tarea.FechaTerminada != null) {
            // 1. Crear un formato bonito (Ej: 15/10/2023 14:30)
            val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

            // 2. Convertir el número largo de la base de datos a fecha real
            val fechaTexto = formato.format(Date(tarea.FechaTerminada!!))

            // 3. Mostrarlo en lugar del tiempo límite
            holder.binding.tvTiempo.text = "Terminada el: $fechaTexto"
        } else {
            // Si no está terminada, mostramos el límite normal
            holder.binding.tvTiempo.text = "Límite: ${tarea.TiempoLimite}"
        }

        // Configuración del CheckBox (igual que antes)
        holder.binding.checkTerminar.setOnCheckedChangeListener(null)
        holder.binding.checkTerminar.isChecked = tarea.Completada

        if (tarea.Completada) {
            holder.binding.tvTitulo.paintFlags = holder.binding.tvTitulo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            // Opcional: Deshabilitar el check en tareas terminadas para que no lo quiten por error
            holder.binding.checkTerminar.isEnabled = false
        } else {
            holder.binding.tvTitulo.paintFlags = holder.binding.tvTitulo.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.binding.checkTerminar.isEnabled = true
        }

        holder.binding.checkTerminar.setOnClickListener {
            if (holder.binding.checkTerminar.isChecked) {
                onCheckClick(tarea)
            }
        }
    }

    override fun getItemCount(): Int = listaTareas.size
}