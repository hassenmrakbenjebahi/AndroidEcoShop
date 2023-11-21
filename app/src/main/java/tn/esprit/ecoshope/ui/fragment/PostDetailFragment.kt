package tn.esprit.ecoshope.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.FragmentPostDetailBinding
import tn.esprit.ecoshope.model.Comment
import tn.esprit.ecoshope.model.Post
import tn.esprit.ecoshope.model.UserConnect
import tn.esprit.ecoshope.ui.adapter.CommentAdapter
import tn.esprit.ecoshope.util.ServiceBuilder
import tn.esprit.ecoshope.util.post.PostService

class PostDetailFragment:Fragment() {
    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var btncomment:ImageView
    private lateinit var idpost: String // Déclaration de idpost au niveau de la classe


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentPostDetailBinding.inflate(layoutInflater)
        // Récupérez le Bundle de la méthode getArguments()
        val    post = arguments?.getParcelable<Post>("p")!!

         btncomment=binding.commentPostBtn
        val iduserconnect="6553fe22539c1e3985881aa2"
         idpost=post.id
        btncomment.setOnClickListener {
            if(!validate()){
                Snackbar.make(binding.root,"Comment is empty",Snackbar.LENGTH_SHORT).show()

            }else {


                val postservice= ServiceBuilder.buildService(PostService::class.java)

                postservice.addComment(
                    idpost,
                    iduserconnect,
                    PostService.newcommentpost(binding.commentField.text.toString())
                ).enqueue(
                    object : Callback<Comment> {
                        override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                            if (response.isSuccessful) {
                                Snackbar.make(
                                    binding.root,
                                    "Comment added successfully",
                                    Snackbar.LENGTH_SHORT
                                ).show()

                            } else {
                                   Snackbar.make(
                                    binding.root,
                                    "Error adding comment",
                                    Snackbar.LENGTH_SHORT
                                ).show()


                            }
                        }

                        override fun onFailure(call: Call<Comment>, t: Throwable) {
                        }
                    }
                )
            }
        }

        val postservicee= ServiceBuilder.buildService(PostService::class.java)

        postservicee.getAllCommentPost(idpost).enqueue(object :Callback<List<Comment>>{
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if(response.isSuccessful){
                    Log.d("commentaret", "onResponse:${response.body()} ")
                    val listcomment=response.body()!!
                    binding.rvComment.adapter = CommentAdapter(listcomment)
                    binding.dBlogCommentCount.text=listcomment.size.toString()



                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
            }

        })


        postservicee.detailUser(post.iduser).enqueue(object :Callback<UserConnect>{
            override fun onResponse(
                call: Call<UserConnect>,
                response: Response<UserConnect>
            ) {if(response.isSuccessful){
                val userpost=response.body()!!

                binding.dBlogUserName.text=userpost.Username
                Glide.with(binding.root)
                    .load(userpost.Image)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.starbucks_background) // Optional placeholder image
                            .error(R.drawable.jk_placeholder_image) // Optional error image
                            .diskCacheStrategy(DiskCacheStrategy.ALL) // Optional: Caching strategy
                    )
                    .into(binding.dBlogUserImage)


            }
            }

            override fun onFailure(call: Call<UserConnect>, t: Throwable) {
                Log.e("Network Error", t.toString())

            }

        })

        if(searchlike(post.likes,iduserconnect)){
            binding.dBlogLike2Btn.visibility=View.VISIBLE

        }else{

            binding.dBlogLikeBtn.visibility=View.VISIBLE

        }



        binding.dBlogLikeBtn.setOnClickListener{
            val servicepost=ServiceBuilder.buildService(PostService::class.java)
            servicepost.addlike(post.id,iduserconnect).enqueue(object:Callback<Post>{
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if(response.isSuccessful){
                        binding.dBlogLike2Btn.visibility=View.VISIBLE
                        binding.dBlogLikeBtn.visibility=View.INVISIBLE
                        //notifyDataSetChanged()


                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Log.e("erroradd", "onFailure: error", )
                }

            })
        }



        binding.dBlogLike2Btn.setOnClickListener{
            val servicepost=ServiceBuilder.buildService(PostService::class.java)
            servicepost.retirelike(post.id,iduserconnect).enqueue(object:Callback<Post>{
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if(response.isSuccessful){
                        binding.dBlogLike2Btn.visibility=View.INVISIBLE
                        binding.dBlogLikeBtn.visibility=View.VISIBLE

                        //notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Log.e("errorretire", "onFailure: error", )
                }

            })
        }



                binding.dBlogDate.text=post.publicationDate
                binding.dBlogDescription.text=post.content
                binding.dBlogLikeCount.text=post.likes.size.toString()
        Glide.with(binding.root)
            .load(post.media)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.starbucks_background) // Optional placeholder image
                    .error(R.drawable.jk_placeholder_image) // Optional error image
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Optional: Caching strategy
            )
            .into(binding.dBlogImage)
               binding.rvComment.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root

    }

     private fun validate():Boolean{
         if(binding.commentField.text!!.isEmpty()){
             return false
         }
         return true

     }
    private fun searchlike( list:List<String>, iduser:String):Boolean{
        var test=false
        for(i in 0 until list.size)
            if(list[i]==iduser){
                test=true
            }
        return test

    }

}