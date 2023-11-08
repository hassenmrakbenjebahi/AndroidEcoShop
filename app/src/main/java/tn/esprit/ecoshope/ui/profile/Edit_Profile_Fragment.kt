package tn.esprit.ecoshope.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tn.esprit.ecoshope.R

class Edit_Profile_Fragment : Fragment() {

    companion object{
        fun newInstance3(): Edit_Profile_Fragment {
            return Edit_Profile_Fragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit__profile_, container, false)
    }
}