package tn.esprit.ecoshope.ui.Home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import tn.esprit.ecoshope.ui.HomeActivity.HomeFragment
import tn.esprit.ecoshope.ui.profile.Edit_Profile_Fragment
import tn.esprit.ecoshope.ui.scanned_hist_fav.Scanned_History_Favourites
import tn.esprit.ecoshope.ui.scanner.ScannerFragment

class HomeAdapter (var fm: FragmentManager): FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment.newInstance()
            1-> ScannerFragment.newInstance2()
            2 -> Scanned_History_Favourites.newInstance(Scanned_History_Favourites.ResultListType.ALL_RESULT)
            3 -> Scanned_History_Favourites.newInstance(Scanned_History_Favourites.ResultListType.FAVOURITE_RESULT)
            4 -> Edit_Profile_Fragment.newInstance3()
            else -> HomeFragment.newInstance()
        }
    }
    override fun getCount(): Int {
        return 5
    }
}