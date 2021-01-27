package com.app.vidflix.Models

/**
 * Created by Prakash Reddy on 1/21/2021.
 */
class Videos {

    private var castImageUrl : String = ""
    private var description : String = ""
    private var director : String = ""
    private var genre : String = ""
    private var mainCast : String = ""
    private var release : String = ""
    private var title : String = ""
    private var vidImageUrl : String = ""
    private var videoUrl : String = ""

    constructor()
    constructor(
        castImageUrl: String,
        description: String,
        director: String,
        genre: String,
        mainCast: String,
        release: String,
        title: String,
        vidImageUrl: String,
        videoUrl : String
    ) {
        this.castImageUrl = castImageUrl
        this.description = description
        this.director = director
        this.genre = genre
        this.mainCast = mainCast
        this.release = release
        this.title = title
        this.vidImageUrl = vidImageUrl
        this.videoUrl = videoUrl
    }

    fun getCastImageUrl():String{
        return castImageUrl
    }
    fun setCastImageUrl(castImageUrl: String){
        this.castImageUrl = castImageUrl
    }

    fun getDescription():String{
        return description
    }
    fun setDescription(description: String){
        this.description = description
    }

    fun getDirector():String{
        return director
    }
    fun setDirector(director: String){
        this.director = director
    }

    fun getGenre():String{
        return genre
    }
    fun setGenre(genre: String){
        this.genre = genre
    }

    fun getMainCast():String{
        return mainCast
    }
    fun setMainCast(mainCast: String){
        this.mainCast = mainCast
    }

    fun getRelease():String{
        return release
    }
    fun setRelease(release: String){
        this.release = release
    }

    fun getTitle():String{
        return title
    }
    fun setTitle(title: String){
        this.title = title
    }

    fun getVidImageUrl():String{
        return vidImageUrl
    }
    fun setVidImageUrl(vidImageUrl: String){
        this.vidImageUrl = vidImageUrl
    }

    fun getVideoUrl():String{
        return videoUrl
    }
    fun setVideoUrl(videoUrl: String){
        this.videoUrl = videoUrl
    }

}