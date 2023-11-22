package tn.esprit.ecoshope.database.db.dialogs

import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.text.ClipboardManager
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.database.db.DBHelper
import tn.esprit.ecoshope.database.db.DBHelperr
import tn.esprit.ecoshope.database.db.DataBase.ScanDataBase
import tn.esprit.ecoshope.model.entites.QrScanResult
import tn.esprit.ecoshope.util.ClientObject
import tn.esprit.ecoshope.util.retrofitProduct.ApiProduct
import tn.esprit.ecoshope.util.retrofitProduct.ApiProductResponse

class ScanDialog (var context : Context) {
    private lateinit var dialog: Dialog

    private var qrResult: QrScanResult? = null
    private var onDismisslistener :onDismissListener? = null
    private lateinit var dbHelper : DBHelper
    var name1: String? =null
    var description: String? =null

    init {
        init()
        initDialog()

    }

    private fun init() {
        dbHelper = DBHelperr(ScanDataBase.getAppDataBase(context)!!)
    }

    private fun initDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.layou_show_result)
        dialog.setCancelable(false)
        onClicks()
    }

    fun setOnDismissListener(onDismissListener: onDismissListener){
        this.onDismisslistener=onDismissListener
    }



    fun show (qrResult: QrScanResult){

        this.qrResult=qrResult
        val date = dialog.findViewById<TextView>(R.id.scannedDate)
        date.text=qrResult.calendar.toFormattedDisplay()
        val scanText =dialog.findViewById<TextView>(R.id.scannedText)
        scanText.text=qrResult.result
        val fav =dialog.findViewById<ImageView>(R.id.favouriteIcon)
        fav.isSelected = qrResult.favourite
        val scnaname = dialog.findViewById<TextView>(R.id.nameproduct)
        val scandescription =dialog.findViewById<TextView>(R.id.descriptionproduct)

        scnaname.text=name1
        scandescription.text=description
        dialog.show()
    }
    fun showproduct(){
        val code = dialog.findViewById<TextView>(R.id.scannedText).text.toString().trim()

        val apiInterface = ClientObject.buildService(ApiProduct::class.java)
        apiInterface.getProduct("$code")
            .enqueue(object : Callback<ApiProductResponse> {
                override fun onResponse(
                    call: Call<ApiProductResponse>,
                    response: Response<ApiProductResponse>
                ) {
                    if (response.isSuccessful){
                         name1 = response.body()?.name
                         description = response.body()?.description
                        Log.d("product", "onResponse:$name1  $description ")

                    }else{
                        Log.e("LoginError", "Response code: ${response.code()}")
                        val errorBody = response.errorBody()?.string()
                        Log.e("LoginError", "Error body: $errorBody")

                    }
                }

                override fun onFailure(call: Call<ApiProductResponse>, t: Throwable) {

                }

            })
    }


    private fun onClicks() {
        val fav = dialog.findViewById<ImageView>(R.id.favouriteIcon)
        val share =dialog.findViewById<ImageView>(R.id.shareResult)
        val copy = dialog.findViewById<ImageView>(R.id.copyResult)
        val cancel = dialog.findViewById<ImageView>(R.id.cancelDialog)

        fav.setOnClickListener {
            if (it.isSelected){
                removeFromFav()

            }else{
                addToFav()
            }
        }
        share.setOnClickListener {
            shareResult()
        }
        copy.setOnClickListener {
            copyResult()
        }
        cancel.setOnClickListener {
            onDismisslistener?.onDismiss()
            dialog.dismiss()
        }
    }

    private fun addToFav() {
        val fav =dialog.findViewById<ImageView>(R.id.favouriteIcon)
        fav.isSelected =true
        dbHelper.addToFavourite(qrResult?.id!!)
    }

    private fun removeFromFav() {
        val fav =dialog.findViewById<ImageView>(R.id.favouriteIcon)
        fav.isSelected =false
        dbHelper.removeFromFavourite(qrResult?.id!!)
    }

    private fun shareResult() {
        val textscan =dialog.findViewById<TextView>(R.id.scannedText)
        val txtIntent = Intent(Intent.ACTION_SEND)
        txtIntent.type = "text/plain"
        txtIntent.putExtra(
            Intent.EXTRA_TEXT,
            textscan.text.toString()
        )
        context.startActivity(txtIntent)
    }

    private fun copyResult() {
        val textscan =dialog.findViewById<TextView>(R.id.scannedText)
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("QrCodeScannedResult", textscan.text)
        clipboard.text = clip.getItemAt(0).text.toString()
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    interface onDismissListener{
        fun onDismiss()
    }

}