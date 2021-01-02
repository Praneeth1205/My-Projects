package com.example.socioapp.Model

class Post {
    private var postid :String =""
    private var description :String =""
    private var publisher :String =""
    private var postimage :String? =""


    constructor()
    constructor(postid: String, description: String, publisher: String, postimage: String) {
        this.postid = postid
        this.description = description
        this.publisher = publisher
        this.postimage = postimage
    }

    fun getPostid():String
    {
        return postid
    }
    fun setPostid(postid: String)
    {
        this.postid = postid
    }
    fun getDescription():String
    {
        return description
    }
    fun setDescription(description: String)
    {
        this.description = description
    }
    fun getPublisher():String
    {
        return publisher
    }
    fun setPublisher(publisher: String)
    {
        this.publisher = publisher
    }
    fun getPostImage():String?
    {
        return postimage
    }
    fun setPostImage(postimage: String)
    {
        this.postimage = postimage
    }

    override fun toString(): String {
        return "Post(postid='$postid', description='$description', publisher='$publisher', postimage=$postimage)"
    }


}