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

class AdaptadorTareaActivity(private val onCheckClick: (TareaEntity) -> Unit) : RecyclerView.Adapter<AdaptadorTareaActivity.TareaCardViewHolder>() {// Check y scroll

    private val dataCards = mutableListOf<TareaEntity>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaCardViewHolder { //Crea Vistas
        context = parent.context
        return TareaCardViewHolder(
            ItemTareaBinding.inflate( //ItemTarea
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TareaCardViewHolder, position: Int) {
        holder.binding(dataCards[position]) //DatosItem
    }

    override fun getItemCount(): Int = dataCards.size //Nro

    //Cada Tarjeta ItemTarea
    inner class TareaCardViewHolder(private val binding: ItemTareaBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(tarea: TareaEntity) {
            binding.tituloItem.text = tarea.Titulo
            binding.descripcion.text = tarea.Descripcion

            if (tarea.Completada) {
                mostrarCompletada(tarea)
            } else {
                mostrarPendiente(tarea)
            }

            binding.checkTerminar.setOnClickListener {
                if (binding.checkTerminar.isChecked) onCheckClick(tarea) //Envia info
            }
        }

        //Historial
        private fun mostrarCompletada(tarea: TareaEntity) {
            binding.checkTerminar.visibility = View.GONE
            //Si Tachado
            binding.tituloItem.paintFlags = binding.tituloItem.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            //Termino Fecha
            tarea.FechaTerminada?.let {
                val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                binding.tiempo.text = "Terminada: ${formato.format(Date(it))}"
            }
        }

        //Tareas
        private fun mostrarPendiente(tarea: TareaEntity) {
            binding.checkTerminar.apply {
                visibility = View.VISIBLE
                isChecked = false
                isEnabled = true
            }
            //No Tachado
            binding.tituloItem.paintFlags = binding.tituloItem.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            binding.tiempo.text = "Límite: ${tarea.TiempoLimite}"
        }
    }
    //Actualizar Items
    fun addDataCards(list: List<TareaEntity>) {
        dataCards.clear()
        dataCards.addAll(list)
        notifyDataSetChanged()
    }
}
