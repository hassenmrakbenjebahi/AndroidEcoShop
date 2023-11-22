package tn.esprit.ecoshope.ui.fragment

import android.app.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.databinding.FragmentAddPostBinding
import tn.esprit.ecoshope.model.Post
import tn.esprit.ecoshope.util.ClientObject
import tn.esprit.ecoshope.util.post.PostService
import java.io.File


class AddPostFragment: Fragment() {
    private lateinit var binding: FragmentAddPostBinding
    private var mediaUri: Uri? = null
    private var filePath: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddPostBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val iduserconnect="6553fe22539c1e3985881aa2"
        binding.AddPost.setOnClickListener{
            val content=binding.addContent.text.toString()
        }
        binding.imageViewBlog.setOnClickListener {
            pickImage()
        }
        binding.AddPost.setOnClickListener {
            // Replace "your_image_path" with the actual path of your image file

            val file= File(filePath)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            Log.d("req", "onViewCreated:$requestFile ")
            val body = MultipartBody.Part.createFormData("media", file.name, requestFile)
            val contentreq=binding.addContent.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val postservice= ClientObject.buildService(PostService::class.java)

            postservice.addpost(iduserconnect,contentreq /*,body*/).enqueue(object :Callback<Post>{
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if(response.isSuccessful){
                        Snackbar.make(binding.root,"post adding successfully",Snackbar.LENGTH_SHORT).show()


                    }else{
                        Snackbar.make(binding.root,"errreuurr",Snackbar.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Log.e("Network Error", t.toString())
                }

            })
        }

    }


    // Gestion de la réponse de l'intent de choix d'image



private fun pickImage(){
    Intent(Intent.ACTION_PICK).also {
        it.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(it, REQUEST_CODE_IMAGE)
    }
}
    companion object {
        const val REQUEST_CODE_IMAGE = 101
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE -> {
                    //mediaUri = data?.data
                    val selectedImageUri = data?.data!!
                    binding.imageViewBlog.setImageURI(selectedImageUri)

                    // Convert selectedImageUri to file path
                     filePath = getImagePathFromUri(selectedImageUri)
                    Log.d("pathfile", "onActivityResult: $filePath")
                }
            }
        }
    }

    private fun getImagePathFromUri(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireContext().contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }

        return ""
    }


}