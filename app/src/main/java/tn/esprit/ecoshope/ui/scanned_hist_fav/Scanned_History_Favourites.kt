package tn.esprit.ecoshope.ui.scanned_hist_fav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tn.esprit.ecoshope.R
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanned__history__favourites, container, false)
    }
}