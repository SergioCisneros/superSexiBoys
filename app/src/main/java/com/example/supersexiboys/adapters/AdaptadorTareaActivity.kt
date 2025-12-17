package com.example.supersexiboys.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.basededatos.TareaEntity
import com.example.supersexiboys.databinding.ItemTareaBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AdaptadorTareaActivity(
    private val onCheckClick: (TareaEntity) -> Unit
) : RecyclerView.Adapter<AdaptadorTareaActivity.TareaCardViewHolder>() {

    private val dataCards = mutableListOf<TareaEntity>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaCardViewHolder {
        context = parent.context
        return TareaCardViewHolder(
            ItemTareaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TareaCardViewHolder, position: Int) {
        holder.binding(dataCards[position])
    }

    override fun getItemCount(): Int = dataCards.size

    inner class TareaCardViewHolder(
        private val binding: ItemTareaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun binding(tarea: TareaEntity) {
            // Mostrar título y descripción
            binding.tvTitulo.text = tarea.Titulo
            binding.descripcion.text = tarea.Descripcion

            // Mostrar fecha de terminación o límite
            if (tarea.Completada && tarea.FechaTerminada != null) {
                val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                val fechaTexto = formato.format(Date(tarea.FechaTerminada!!))
                binding.tiempo.text = "Terminada el: $fechaTexto"
            } else {
                binding.tiempo.text = "Límite: ${tarea.TiempoLimite}"
            }

            // Configurar CheckBox según si la tarea está completada
            binding.checkTerminar.isChecked = tarea.Completada
            binding.checkTerminar.isEnabled = !tarea.Completada

            // Tachado en el título si está completada
            if (tarea.Completada) {
                binding.tvTitulo.paintFlags = binding.tvTitulo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.tvTitulo.paintFlags = binding.tvTitulo.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            // Click para marcar como completada
            binding.checkTerminar.setOnClickListener {
                if (binding.checkTerminar.isChecked) {
                    onCheckClick(tarea)
                }
            }
        }

    }

    fun addDataCards(list: List<TareaEntity>) {
        dataCards.clear()
        dataCards.addAll(list)
        notifyDataSetChanged()
    }
}
