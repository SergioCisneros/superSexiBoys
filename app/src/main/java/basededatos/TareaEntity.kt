package com.example.supersexiboys.basededatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TareaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "titulo") val titulo: String,
    @ColumnInfo(name = "descripcion") val descripcion: String,
    @ColumnInfo(name = "tiempo_limite") val tiempoLimite: String,
    @ColumnInfo(name = "etiquetas") val etiquetas: String,
    @ColumnInfo(name = "completada", defaultValue = "0") val completada: Boolean = false,
    @ColumnInfo(name = "fecha_terminada") val fechaTerminada: Long? = null
)