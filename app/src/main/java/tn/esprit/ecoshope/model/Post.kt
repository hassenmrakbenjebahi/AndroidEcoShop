package tn.esprit.ecoshope.model

class Post(
             val id:String,
             val author: String,
             val content: String,
             val publicationDate:String,
            val comment:List<Comment>,
            val like:List<String>
    )



