package com.rynkbit.coffeestore.data.converter

import androidx.room.TypeConverter
import java.util.*


class DateConverter{
    @TypeConverter
    fun fromTimestamp(timestamp: Long) : Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
}