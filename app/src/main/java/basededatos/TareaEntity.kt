package com.example.supersexiboys.basededatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TareaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "usuario_id") val usuarioId: String, //
    @ColumnInfo(name = "titulo") val Titulo: String,
    @ColumnInfo(name = "descripcion") val Descripcion: String,
    @ColumnInfo(name = "tiempo_limite") val TiempoLimite: String,
    @ColumnInfo(name = "etiquetas") val Etiquetas: String,
    @ColumnInfo(name = "completada", defaultValue = "0") var Completada: Boolean = false,
    @ColumnInfo(name = "fecha_terminada") var FechaTerminada: Long? = null
)
