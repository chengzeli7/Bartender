package com.lcz.bartender.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lcz.bartender.presentation.IngredientAmount
import java.util.Date

/**
 * Room 数据库的类型转换器，用于将复杂对象（如 List<String>、List<IngredientAmount> 和 Date）
 * 转换为 Room 可以存储的字符串或长整型格式，反之亦然。
 */
class Converters {
    // Gson 实例用于 JSON 序列化和反序列化
    private val gson = Gson()

    /**
     * 将 List<String> 转换为 JSON 字符串。
     * @param list 要转换的字符串列表。
     * @return 对应的 JSON 字符串。
     */
    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return gson.toJson(list)
    }

    /**
     * 将 JSON 字符串转换为 List<String>。
     * @param json 要转换的 JSON 字符串。
     * @return 对应的字符串列表。
     */
    @TypeConverter
    fun toStringList(json: String?): List<String>? {
        if (json == null) return null
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }

    /**
     * 将 List<IngredientAmount> 转换为 JSON 字符串。
     * @param list 要转换的 IngredientAmount 列表。
     * @return 对应的 JSON 字符串。
     */
    @TypeConverter
    fun fromIngredientAmountList(list: List<IngredientAmount>?): String? {
        return gson.toJson(list)
    }

    /**
     * 将 JSON 字符串转换为 List<IngredientAmount>。
     * @param json 要转换的 JSON 字符串。
     * @return 对应的 IngredientAmount 列表。
     */
    @TypeConverter
    fun toIngredientAmountList(json: String?): List<IngredientAmount>? {
        if (json == null) return null
        val type = object : TypeToken<List<IngredientAmount>>() {}.type
        return gson.fromJson(json, type)
    }

    /**
     * 将 Date 对象转换为 Long (时间戳)。
     * Room 可以存储 Long 类型。
     * @param date 要转换的 Date 对象。
     * @return 对应的 Long 时间戳，如果 Date 为 null 则返回 null。
     */
    @TypeConverter
    fun fromTimestamp(date: Date?): Long? {
        return date?.time
    }

    /**
     * 将 Long (时间戳) 转换为 Date 对象。
     * @param timestamp 要转换的 Long 时间戳。
     * @return 对应的 Date 对象，如果时间戳为 null 则返回 null。
     */
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}
