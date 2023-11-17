package tn.esprit.ecoshope.database.db

import tn.esprit.ecoshope.database.db.DataBase.ScanDataBase
import tn.esprit.ecoshope.model.entites.QrScanResult
import java.util.Calendar

class DBHelperr (var scanDataBase: ScanDataBase) :DBHelper{
    override fun insertQrResult(result: String): Int {
        val time = Calendar.getInstance()
        val resultType = "TEXT"
        val qrResult = QrScanResult(result= result, resulttype = resultType , calendar = time, favourite = false)
        return scanDataBase.getDao().insertQrresult(qrResult).toInt()
    }

    override fun getQRResult(id: Int): QrScanResult {
        return scanDataBase.getDao().getscanresult(id)
    }

    override fun addToFavourite(id: Int): Int {
        return scanDataBase.getDao().addTofav(id)
    }

    override fun removeFromFavourite(id: Int): Int {
        return scanDataBase.getDao().removeFromfav(id)
    }

    override fun getAllQRScannedResult(): List<QrScanResult> {
        return scanDataBase.getDao().getallscanresult()
    }

    override fun getAllFavouriteQRScannedResult(): List<QrScanResult> {
        return scanDataBase.getDao().getallfavscan()
    }
}