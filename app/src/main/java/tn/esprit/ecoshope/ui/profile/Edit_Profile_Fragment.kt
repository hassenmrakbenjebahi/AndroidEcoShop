package tn.esprit.ecoshope.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Trouvez le bouton des paramètres et configurez le click listener
        val settings = view.findViewById<ImageView>(R.id.imagesettings)
        settings.setOnClickListener {
            startActivity(Intent(context, SettingsActivity::class.java))
        }
    }


}