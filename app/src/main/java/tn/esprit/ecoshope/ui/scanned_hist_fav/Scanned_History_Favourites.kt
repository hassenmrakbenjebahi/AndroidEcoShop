package tn.esprit.ecoshope.ui.scanned_hist_fav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.database.db.DBHelper
import tn.esprit.ecoshope.database.db.DBHelperr
import tn.esprit.ecoshope.database.db.DataBase.ScanDataBase
import tn.esprit.ecoshope.database.db.dialogs.gone
import tn.esprit.ecoshope.database.db.dialogs.visible
import tn.esprit.ecoshope.model.entites.QrScanResult
import tn.esprit.ecoshope.ui.scanner.adapter.ScanResultAdapter
import java.io.Serializable

class Scanned_History_Favourites : Fragment() {
    enum class ResultListType : Serializable {
        ALL_RESULT, FAVOURITE_RESULT
    }

    companion object {
        private const val ARGUMENT_RESULT_LIST_TYPE = "ArgumentResultType"

        fun newInstance(screenType: ResultListType): Scanned_History_Favourites {
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENT_RESULT_LIST_TYPE, screenType)
            val fragment = Scanned_History_Favourites()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments()
    }

    private lateinit var resultListType: ResultListType
    private lateinit var mView: View
    private lateinit var dbHelper: DBHelper

    private fun handleArguments() {
        resultListType = requireArguments().getSerializable(ARGUMENT_RESULT_LIST_TYPE) as ResultListType
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView= inflater.inflate(R.layout.fragment_scanned__history__favourites, container, false)
        init()
        showListResults()
        setswipeRefreshLayout()
        return mView
    }

    private fun setswipeRefreshLayout() {
        val swipe = mView.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        swipe.setOnRefreshListener {
            swipe.isRefreshing=false
            showListResults()
        }
    }

    private fun showListResults() {
        when(resultListType){
            ResultListType.ALL_RESULT -> {
                showallResult()
            }
            ResultListType.FAVOURITE_RESULT ->{
                showfavResult()
            }
        }
    }

    private fun showallResult() {
        var listOfResult =  dbHelper.getAllQRScannedResult()
        showResults(listOfResult)
        val header =mView.findViewById<TextView>(R.id.tvHeaderText)
        header.text="Recent Scanned"
    }

    private fun showfavResult() {
        var listOffavResult =  dbHelper.getAllFavouriteQRScannedResult()
        showResults(listOffavResult)
        val header =mView.findViewById<TextView>(R.id.tvHeaderText)
        header.text="Recent Favourites Scanned"
    }

    private fun showResults(listOfQRResult: List<QrScanResult>) {
        if (listOfQRResult.isEmpty()){
            showEmpty()
        }else{
            initRecycleView(listOfQRResult)
        }
    }

    private fun initRecycleView(listOfQRResult: List<QrScanResult>) {
        val empty = mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView)
        empty.layoutManager= LinearLayoutManager(requireContext())
        empty.adapter= ScanResultAdapter(dbHelper,requireContext(), listOfQRResult.toMutableList())
    }

    private fun showEmpty() {
        val empty = mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView)
        empty.gone()
        val visible = mView.findViewById<ImageView>(R.id.noResultFound)
        visible.visible()
    }



    private fun init() {
        dbHelper = DBHelperr(ScanDataBase.getAppDataBase(requireContext())!!)
    }

}