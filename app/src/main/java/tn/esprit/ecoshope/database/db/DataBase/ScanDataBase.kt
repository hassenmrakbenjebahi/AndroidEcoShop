package tn.esprit.ecoshope.database.db.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tn.esprit.ecoshope.database.db.dao.QrDao
import tn.esprit.ecoshope.model.entites.QrScanResult

@Database(entities = [QrScanResult::class], version = 1, exportSchema = false)
abstract class ScanDataBase : RoomDatabase() {
    abstract fun getDao(): QrDao

    companion object {
        private const val DB_Name = "QrScanDataBase"
        private var QrScanResultDataBase: ScanDataBase? = null

        fun getAppDataBase(context: Context): ScanDataBase? {
            if (QrScanResultDataBase == null) {
                QrScanResultDataBase = Room.databaseBuilder(
                    context.applicationContext,
                    ScanDataBase::class.java,
                    DB_Name
                ).allowMainThreadQueries().build()
            }
            return QrScanResultDataBase
        }

    }
}