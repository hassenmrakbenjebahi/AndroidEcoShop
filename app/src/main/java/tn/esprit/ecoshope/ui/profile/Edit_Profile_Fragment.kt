package tn.esprit.ecoshope.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Picasso
import tn.esprit.ecoshope.MainActivity
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.R.drawable
import javax.sql.DataSource

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
        val sharedPreferences = requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("USER_NAME", "Default Name")
        val userEmail = sharedPreferences.getString("USER_EMAIL", "Default Email")
        val userPhotoUrl = sharedPreferences.getString("USER_PHOTO_URL", "")

        val nameTextView = view.findViewById<TextView>(R.id.name_profile)
        val emailTextView = view.findViewById<TextView>(R.id.email_profile)
        var photoImageView = view.findViewById<ImageView>(R.id.iamge_profile)
       // Log.d("ProfileFragment", "User photo URL: $userPhotoUrl")

        nameTextView.text = userName
        emailTextView.text = userEmail

        // Assurez-vous que 'this' est une instance de Context (comme une Activity) ou utilisez getContext() dans un Fragment.
      /*  Picasso.get()
            .load(userPhotoUrl)
            .error(R.drawable.outline_person_24) // Image à afficher en cas d'erreur
            .into(photoImageView)*/


         Glide.with(this)
             .load(userPhotoUrl)
             // Image de remplacement pendant le chargement
             .error(R.drawable.outline_person_24)
            .into(photoImageView)



       /* val sharedPreferences2 = requireActivity().getSharedPreferences("MyApp2", Context.MODE_PRIVATE)
        val userName2 = sharedPreferences2.getString("USER_NAME2", "Default Name")
        val userEmail2 = sharedPreferences2.getString("USER_EMAIL2", "Default Email")
        val userPhotoUrl2 = sharedPreferences2.getString("USER_PHOTO_URL2", "")
        view.findViewById<TextView>(R.id.name_profile).text = userName2
        view.findViewById<TextView>(R.id.email_profile).text = userEmail2
        Glide.with(this)
            .load(userPhotoUrl2)
            .error(R.drawable.outline_person_24)
            .into(photoImageView)*/


               /* if (userPhotoUrl!!.isNotEmpty()) {
                    Glide.with(this) // Assurez-vous que 'this' est un Context ou une Activity/Fragment valide.
                        .load(userPhotoUrl)
                        //.placeholder(drawable) // Une image par défaut pendant le chargement
                        .error(R.drawable.baseline_person_24) // Une image en cas d'erreur de chargement
                        .into(photoImageView)
                }*/


        val logout1 = view.findViewById<ImageView>(R.id.logout)
        logout1.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
        val sharedPreferences = requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("JWT_TOKEN")
        editor.apply()
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


}