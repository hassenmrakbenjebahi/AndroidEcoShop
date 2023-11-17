package tn.esprit.ecoshope.database.db.convertTime

import androidx.room.TypeConverter
import java.util.Calendar

class TimeConvert {
    @TypeConverter
    fun toCalendar(l: Long): Calendar? {
        val c = Calendar.getInstance()
        c!!.timeInMillis = l
        return c
    }

    @TypeConverter
    fun fromCalendar(c: Calendar?): Long? {
        return c?.time?.time
    }
}