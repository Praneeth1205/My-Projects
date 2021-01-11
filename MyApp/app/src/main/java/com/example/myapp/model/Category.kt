package com.example.myapp.model


class Category{
    private var name : String = ""
    private var imageUrl : String = ""

    constructor()
    constructor(name: String, imageUrl: String) {
        this.name = name
        this.imageUrl = imageUrl
    }

    fun getName() : String
    {
      return name
    }
    fun getImage() : String
    {
        return imageUrl
    }
    fun setName(name: String)
    {
        this.name = name
    }
    fun setImage(imageUrl: String)
    {
        this.imageUrl = imageUrl
    }

}