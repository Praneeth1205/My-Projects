package com.blood.ease.learning.model

class Source {

    private var description : String = ""
    private var author : String = ""
    private var title : String = ""
    private var imageUrl : String = ""
    private var videoUrl : String = ""

    constructor()
    constructor(
        description: String,
        director: String,
        title: String,
        imageUrl: String,
        videoUrl : String
    ) {
        this.description = description
        this.author = director
        this.title = title
        this.imageUrl = imageUrl
        this.videoUrl = videoUrl
    }


    fun getDescription():String{
        return description
    }
    fun setDescription(description: String){
        this.description = description
    }

    fun getAuthor():String{
        return author
    }
    fun setAuthor(director: String){
        this.author = director
    }

    fun getTitle():String{
        return title
    }
    fun setTitle(title: String){
        this.title = title
    }

    fun getImageUrl():String{
        return imageUrl
    }
    fun setImageUrl(vidImageUrl: String){
        this.imageUrl = vidImageUrl
    }

    fun getVideoUrl():String{
        return videoUrl
    }
    fun setVideoUrl(videoUrl: String){
        this.videoUrl = videoUrl
    }
}