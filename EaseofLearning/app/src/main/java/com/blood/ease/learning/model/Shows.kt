package com.blood.ease.learning.model

class Shows {
    private var imageUrl : String = ""
    private var showname : String = ""

    constructor()
    constructor(imageUrl: String, showname: String) {
        this.imageUrl = imageUrl
        this.showname = showname
    }

    fun setImageUrl(imageUrl: String){
        this.imageUrl =imageUrl
    }
    fun getImageUrl() : String{
        return imageUrl
    }

    fun setShowName(showname: String){
        this.showname = showname
    }
    fun getShowName():String{
        return showname
    }
}