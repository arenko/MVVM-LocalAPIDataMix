package com.arenko.rijksapp.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArtConverter {

    @TypeConverter
    fun toString(value: ArtImage): String {
        val gson = Gson()
        val type = object : TypeToken<ArtImage>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toObj(value: String): ArtImage {
        val gson = Gson()
        val type = object : TypeToken<ArtImage>() {}.type
        return gson.fromJson(value, type)
    }
}