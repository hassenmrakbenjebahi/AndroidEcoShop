package tn.esprit.ecoshope.database.db

import tn.esprit.ecoshope.model.entites.QrScanResult

interface DBHelper {
        fun insertQrResult(result: String):Int
        fun getQRResult(id: Int): QrScanResult
        fun addToFavourite(id: Int): Int
        fun removeFromFavourite(id: Int): Int
        fun getAllQRScannedResult(): List<QrScanResult>
        fun getAllFavouriteQRScannedResult(): List<QrScanResult>
}