package com.example.supersexiboys.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supersexiboys.basededatos.HabitoEntity
import com.example.supersexiboys.databinding.AdapterHabitoBinding
import java.time.LocalDateTime

class HabitoAdapter(
    private val paraUpdate: (HabitoEntity) -> Unit
): RecyclerView.Adapter<HabitoAdapter.HabitoCardViewHolder>() {
    private val dataCards = mutableListOf<HabitoEntity>()
    private var context: Context? = null
    val adapterEtiqueta: EtiquetaAdapter by lazy { EtiquetaAdapter() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitoCardViewHolder {
        context = parent.context
        return HabitoCardViewHolder(
            AdapterHabitoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HabitoCardViewHolder, position: Int) {
        holder.binding(dataCards[position])
    }

    override fun getItemCount(): Int = dataCards.size

    inner class HabitoCardViewHolder(private val binding: AdapterHabitoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: HabitoEntity) {
            binding.textTitulo.text = data.titulo
            binding.textDescripcion.text = data.descripcion
            binding.textContador.setText("${data.vecesHecho}/${data.repeticiones}")

            binding.recyclerEtiquetas.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapterEtiqueta.addDataCards(toList(data.etiquetas))
            binding.recyclerEtiquetas.adapter = adapterEtiqueta

            val fechaHora = LocalDateTime.now()
            val listCompletados = toIntList(data.vecesCompletado)
            if(data.frecuencia=="Semanal"){
                if(fechaHora.dayOfWeek.value == 1){
                    binding.buttonContador.setText("Ya lo hice")
                    var valCompletado = 0
                    val tam = listCompletados.size
                    if(data.vecesHecho == data.repeticiones){
                        valCompletado = 1
                    }
                    listCompletados.add(listCompletados.get(tam-1)+valCompletado)
                    val tiempoRequerido: Int = (21+data.repeticiones)/data.repeticiones
                    if((tam+1)>=tiempoRequerido){
                        val nroCompletos = listCompletados.get(tam) - listCompletados.get(tam-tiempoRequerido)
                        if(nroCompletos >= (tiempoRequerido-1)){
                            binding.textTipoHabito.setText("(Hábito Fuerte)")
                        }
                        else{
                            binding.textTipoHabito.setText("(Hábito Débil)")
                        }
                    }
                    data.vecesHecho=0
                    data.vecesCompletado = fromIntList(listCompletados)
                    paraUpdate(data)
                }
            }
            else{
                if(fechaHora.hour==0){
                    binding.buttonContador.setText("Ya lo hice")
                    var valCompletado = 0
                    val tam = listCompletados.size
                    if(data.vecesHecho == data.repeticiones){
                        valCompletado = 1
                    }
                    listCompletados.add(listCompletados.get(tam-1)+valCompletado)
                    val tiempoRequerido: Int = 21
                    if((tam+1)>=tiempoRequerido){
                        val nroCompletos = listCompletados.get(tam) - listCompletados.get(tam-tiempoRequerido)
                        if(nroCompletos >= (tiempoRequerido-1)){
                            binding.textTipoHabito.setText("(Hábito Fuerte)")
                        }
                        else{
                            binding.textTipoHabito.setText("(Hábito Débil)")
                        }
                    }
                    data.vecesHecho=0
                    data.vecesCompletado = fromIntList(listCompletados)
                    paraUpdate(data)
                }
            }
            binding.buttonContador.setOnClickListener {
                if(data.vecesHecho!=data.repeticiones){
                    data.vecesHecho+=1
                    if(data.vecesHecho==data.repeticiones){
                        binding.buttonContador.setText("LOGRADO")
                    }
                    Log.v("antes de paraupdate","todo bien")
                    paraUpdate(data)
                    Log.v("despues de paraupdate","todo bien")
                }
                binding.textContador.setText("${data.vecesHecho}/${data.repeticiones}")
            }
        }
    }

    fun addDataCards(list: List<HabitoEntity>) {
        dataCards.clear()
        dataCards.addAll(list)
    }
    fun toList(value: String): List<String> {
        return value.split(",") // convierte el string de vuelta a lista
    }
    fun fromIntList(value: MutableList<Int>?): String {
        return value?.joinToString(",") ?: ""
    }
    fun toIntList(value: String): MutableList<Int> {
        return if (value.isEmpty()) mutableListOf()
        else value.split(",").map { it.toInt() }.toMutableList()
    }


}