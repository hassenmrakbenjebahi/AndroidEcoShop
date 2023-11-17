package tn.esprit.ecoshope.model.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import tn.esprit.ecoshope.database.db.convertTime.TimeConvert
import java.util.Calendar

@Entity
@TypeConverters(TimeConvert::class)
data class QrScanResult (
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,

    @ColumnInfo("result")
    val result : String?,

    @ColumnInfo("result_Type")
    val resulttype : String?,

    @ColumnInfo("favourite")
    val favourite : Boolean = false,

    @ColumnInfo("time")
    val calendar : Calendar
)