package com.velox.org.features.utils

import android.content.Context
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

@TypeConverter
inline fun <reified T : Any?> List<T>.castToString(): String {
    require(this.isNotEmpty()) { THROW_LIST_EXCEPTION }
    return Gson().toJson(this)
}

@TypeConverter
inline fun <reified T : Any?> String.castToList(): List<T> {
    require(this.isNotEmpty()) { THROW_STRING_EXCEPTION }
    return Gson().fromJson(this, object : TypeToken<List<T?>?>() {}.type)
}

@TypeConverter
inline fun <reified T : Any?> String.castToMutableList(): MutableList<T> {
    require(this.isNotEmpty()) { THROW_STRING_EXCEPTION }
    return Gson().fromJson(this, object : TypeToken<MutableList<T?>?>() {}.type)
}

@TypeConverter
inline fun <reified T : Any?> T.castToJson(): String {
    return Gson().toJson(this) ?: EMPTY
}

@TypeConverter
fun String.castToMap(): Map<String, String> {
    val map = this.replace("{", "")
    val map1 = map.replace("}", "")
    return map1.split(",")
        .map { it.split("=") }
        .map { it.first() to it.last() }
        .toMap()
}

@TypeConverter
inline fun <reified T : Any?> String.castToClass(): T? {
    require(this.isNotEmpty()) { THROW_LIST_EXCEPTION }
    return Gson().fromJson(this, T::class.java) ?: null
}

inline fun <reified T : Any> List<T>.castToListUpdate(newValue: T, block: (T) -> Boolean): List<T> {
    require(this.isNotEmpty()) { THROW_LIST_EXCEPTION }
    return map {
        if (block(it)) newValue else it
    }
}

@TypeConverter
fun String.castToStringList(): List<String> {
    require(this.isNotEmpty()) { THROW_STRING_EXCEPTION }
    return this.split(",").toList()
}

@Throws(IOException::class)
fun Context.readJsonAsset(fileName: String): String {
    val inputStream = assets.open(fileName)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    return String(buffer, Charsets.UTF_8)
}

const val EMPTY = ""
const val THROW_LIST_EXCEPTION = "Not a valid Array!"
const val THROW_STRING_EXCEPTION = "Parameter must not be empty!"
