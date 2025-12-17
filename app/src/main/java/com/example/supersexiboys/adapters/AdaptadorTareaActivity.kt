package com.example.supersexiboys.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
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
            // 1. Mostrar textos básicos
            binding.tvTitulo.text = tarea.Titulo
            binding.descripcion.text = tarea.Descripcion

            if (tarea.Completada) {
                // Si la tarea está completada se va al historia;
                binding.checkTerminar.visibility = View.GONE

                binding.tvTitulo.paintFlags = binding.tvTitulo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                // Mostrar fecha de finalización
                if (tarea.FechaTerminada != null) {
                    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    binding.tiempo.text = "Terminada: ${formato.format(Date(tarea.FechaTerminada!!))}"
                }
            } else {
                // Si la tarea está pendiente
                binding.checkTerminar.visibility = View.VISIBLE
                binding.checkTerminar.isChecked = false
                binding.checkTerminar.isEnabled = true

                // Quitar tachado si la vista se está reciclando
                binding.tvTitulo.paintFlags = binding.tvTitulo.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                binding.tiempo.text = "Límite: ${tarea.TiempoLimite}"
            }

            //click para marcar como terminada
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
