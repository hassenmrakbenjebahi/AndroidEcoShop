package tn.esprit.ecoshope.database.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tn.esprit.ecoshope.model.entites.QrScanResult

@Dao
interface QrDao {
    @Query("SELECT * FROM QrScanResult ORDER BY time DESC")
    fun getallscanresult() : List<QrScanResult>

    @Query("SELECT * FROM QrScanResult WHERE favourite = 1 ")
    fun getallfavscan() : List<QrScanResult>

    @Query("DELETE FROM QrScanResult")
    fun deletallscan()

    @Query("DELETE FROM QrScanResult WHERE favourite = 1")
    fun deleteallfav()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQrresult(qrresult : QrScanResult) : Long

    @Query("SELECT * FROM QrScanResult WHERE id = :id")
    fun getscanresult(id:Int) : QrScanResult

    @Query("UPDATE QrScanResult SET favourite = 1 WHERE id = :id")
    fun addTofav(id: Int):Int

    @Query("UPDATE QrScanResult SET favourite = 0 WHERE id = :id")
    fun removeFromfav(id: Int):Int
}