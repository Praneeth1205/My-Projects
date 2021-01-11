package com.example.myapp.model

class User
{
    private var username : String = ""
    private var fullname : String = ""
    private var image : String = ""
    private var uid : String = ""

    constructor()
    constructor(userName: String, fullName: String, profileImage: String,uid : String) {
        this.username = userName
        this.fullname = fullName
        this.image = profileImage
        this.uid = uid
    }

    fun getUserName() : String
    {
        return username
    }
    fun getFullName() : String
    {
        return fullname
    }
    fun getProfileImage() : String
    {
        return image
    }
    fun setUserName(userName: String)
    {
        this.username = userName
    }
    fun setFullName(fullName: String)
    {
        this.fullname = fullName
    }
    fun setProfileImage(profileImage: String)
    {
        this.image = profileImage
    }
    fun getUID():String
    {
        return uid
    }
    fun setUID(uid: String)
    {
        this.uid = uid
    }
}