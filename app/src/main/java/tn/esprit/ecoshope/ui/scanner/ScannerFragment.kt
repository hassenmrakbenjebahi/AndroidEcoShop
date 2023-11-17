package tn.esprit.ecoshope.ui.scanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.database.db.DBHelper
import tn.esprit.ecoshope.database.db.DBHelperr
import tn.esprit.ecoshope.database.db.DataBase.ScanDataBase
import tn.esprit.ecoshope.database.db.dialogs.ScanDialog


class ScannerFragment : Fragment(),ZXingScannerView.ResultHandler {
    companion object{
        fun newInstance2(): ScannerFragment{
            return ScannerFragment()
        }
    }



    private lateinit var mView: View
    private lateinit var scannerView : ZXingScannerView
    private lateinit var resultDialog: ScanDialog
    private lateinit var dbHelper: DBHelper

    //ZBarScannerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView= inflater.inflate(R.layout.fragment_scanner, container, false)
        init()
        initViews()
        onclick()
        return mView.rootView
    }

    private fun init() {
        dbHelper = DBHelperr(ScanDataBase.getAppDataBase(requireContext())!!)
    }

    private fun initViews() {
        initializeQrScanner()
        setResultDialog()
    }

    private fun setResultDialog() {
        resultDialog = ScanDialog(requireContext())
        resultDialog.setOnDismissListener(object : ScanDialog.onDismissListener{
            override fun onDismiss() {
                scannerView.resumeCameraPreview(this@ScannerFragment)
            }

        })
    }

    private fun onclick() {
        val flash = mView.findViewById<ImageView>(R.id.flashicon)
        flash.setOnClickListener {
            if (it.isSelected){
                offflash()
            }else {
                onflash()
            }
        }
    }

    private fun offflash() {
        val a = mView.findViewById<ImageView>(R.id.flashicon)
        a.isSelected = false
        scannerView.flash = false
    }

    private fun onflash() {
        val a = mView.findViewById<ImageView>(R.id.flashicon)
        a.isSelected = true
        scannerView.flash = true
    }

    private fun initializeQrScanner() {
        scannerView = ZXingScannerView(requireContext())
        scannerView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorTranslucent))
        scannerView.setBorderColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
        scannerView.setLaserColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setAutoFocus(true)
        scannerView.setResultHandler(this)
        scannerView.setSquareViewFinder(true)
        var x = mView.findViewById<ViewGroup>(R.id.camerascanner)
        x.addView(scannerView)
        startQrCamera()

    }


    private fun startQrCamera(){
        scannerView.startCamera()
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()

    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        onQrResult(rawResult!!.text)
        Toast.makeText(context, rawResult?.text, Toast.LENGTH_SHORT).show()

    }

    private fun onQrResult(text: String?) {
        if (text.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Empty Qr Code",Toast.LENGTH_SHORT).show()
        }else{
            saveToDatabase(text)
        }
    }

    private fun saveToDatabase(result: String) {
        val insertRowId = dbHelper.insertQrResult(result)
        val qrResult = dbHelper.getQRResult(insertRowId)
        resultDialog.show(qrResult)
    }



}