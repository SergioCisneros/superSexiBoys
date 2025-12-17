package com.example.supersexiboys.basededatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HabitoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "usuario_id") val usuarioId: String,
    @ColumnInfo(name = "titulo") val titulo: String,
    @ColumnInfo(name = "descripcion") val descripcion: String,
    @ColumnInfo(name = "frecuencia") val frecuencia: String,
    @ColumnInfo(name = "repeticiones") val repeticiones: Int,
    @ColumnInfo(name = "etiquetas") val etiquetas: String,
    @ColumnInfo(name = "veces_hecho") var vecesHecho: Int

)
