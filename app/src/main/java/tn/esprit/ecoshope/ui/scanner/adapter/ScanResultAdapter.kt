package tn.esprit.ecoshope.ui.scanner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.database.db.DBHelper
import tn.esprit.ecoshope.database.db.dialogs.ScanDialog
import tn.esprit.ecoshope.database.db.dialogs.gone
import tn.esprit.ecoshope.database.db.dialogs.toFormattedDisplay
import tn.esprit.ecoshope.database.db.dialogs.visible
import tn.esprit.ecoshope.model.entites.QrScanResult

class ScanResultAdapter(
    var dbHelperI: DBHelper,
    var context: Context,
    private var listOfScannedResult: MutableList<QrScanResult>
) : RecyclerView.Adapter<ScanResultAdapter.ScannedResultListViewHolder>() {

    private var resltDialog = ScanDialog(context)

    inner class ScannedResultListViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        fun bind(qrResult: QrScanResult, position: Int){
            val result=view.findViewById<TextView>(R.id.result)
            val time =view.findViewById<TextView>(R.id.tvTime)
            result.text=qrResult.result
            time.text=qrResult.calendar.toFormattedDisplay()
            setFavourite(qrResult.favourite)
            onClicks(qrResult)
        }

        private fun onClicks(qrResult :QrScanResult) {
            view.setOnClickListener {
                resltDialog.show(qrResult)
            }
        }

        private fun setFavourite(favourite: Boolean) {
            val fav =view.findViewById<ImageView>(R.id.favouriteIcon)
            if (favourite){
                fav.visible()
            }else{
                fav.gone()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedResultListViewHolder {
        return ScannedResultListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_single_item_qr_result,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfScannedResult.size
    }

    override fun onBindViewHolder(holder: ScannedResultListViewHolder, position: Int) {
        holder.bind(listOfScannedResult[position],position)
    }
}