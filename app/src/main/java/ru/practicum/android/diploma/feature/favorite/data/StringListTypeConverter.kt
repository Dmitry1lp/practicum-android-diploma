package ru.practicum.android.diploma.feature.favorite.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun fromStringList(list: List<String>): String? = gson.toJson(list)

    @TypeConverter
    fun toStringList(json: String?): List<String>? {
        if (json == null) return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }

}
