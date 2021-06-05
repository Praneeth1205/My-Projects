package com.example.application.models

class User {
    private var username : String = ""
    private var uid : String = ""
    private var phone : String = ""
    private var email : String = ""
    private var imageUrl : String = ""

    constructor()
    constructor(username: String, uid: String, phone: String, email: String, imageUrl: String) {
        this.username = username
        this.uid = uid
        this.phone = phone
        this.email = email
        this.imageUrl = imageUrl
    }

    fun getUserName():String{
        return username
    }
    fun setUserName(username: String){
        this.username = username
    }

    fun getUID():String{
        return uid
    }
    fun setUID(uid: String){
        this.uid = uid
    }

    fun getPhone():String{
        return phone
    }
    fun setPhone(phone: String){
        this.phone = phone
    }

    fun getEmail():String{
        return email
    }
    fun setEmail(email: String){
        this.email = email
    }

    fun getImageUrl():String{
        return imageUrl
    }
    fun setImageUrl(imageUrl: String){
        this.imageUrl = imageUrl
    }
}