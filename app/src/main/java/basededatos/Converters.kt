package com.example.supersexiboys.basededatos

import androidx.room.TypeConverter

// necesaraimente debemos usar TypeConverter, para guardar la MutableList

class Converters {

    @TypeConverter
    fun fromList(list: MutableList<String>): String {
        return list.joinToString("||")
    }

    @TypeConverter
    fun toList(data: String): MutableList<String> {
        return if (data.isEmpty()) mutableListOf()
        else data.split("||").toMutableList()
    }
}
