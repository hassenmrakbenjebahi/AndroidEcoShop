package tn.esprit.ecoshope.ui.scanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tn.esprit.ecoshope.R

class ScannerFragment : Fragment() {
    companion object{
        fun newInstance2(): ScannerFragment{
            return ScannerFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }
}