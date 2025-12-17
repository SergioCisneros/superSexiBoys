package com.example.supersexiboys.basededatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MetaEntity(
    @PrimaryKey(autoGenerate = true) val Id: Int = 0,
    @ColumnInfo(name = "titulo") val Titulo: String,
    @ColumnInfo(name = "descripcion") val Descripcion: String,
    @ColumnInfo(name = "fecha_inicio") val FechaInicio: String,
    @ColumnInfo(name = "fecha_limite") val FechaLimite: String,
    @ColumnInfo(name = "etiquetas") val Etiqueta: String?,
    @ColumnInfo(name = "actividades") val Actividades: MutableList<String> = mutableListOf(""),
    @ColumnInfo(name = "completada", defaultValue = "0") var Completada: Boolean = false,
    @ColumnInfo(name = "fecha_terminada") var FechaTerminada: Long? = null
)