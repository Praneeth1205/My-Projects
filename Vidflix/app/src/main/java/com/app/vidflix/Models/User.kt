package com.app.vidflix.Models

class User {
    private var uid : String =""
    private var fullname : String =""
    private var username : String =""
    private var email : String =""

    constructor()
    constructor(uid: String, fullname: String, username: String, email: String) {
        this.uid = uid
        this.fullname = fullname
        this.username = username
        this.email = email
    }

    fun getUID():String
    {
        return uid
    }

    fun setUID(uid: String){
        this.uid = uid
    }

    fun getUserName():String{
        return username
    }
    fun setUserName(username: String){
        this.username = username
    }
    fun getFullName() : String
    {
        return fullname
    }
    fun setFullName(fullName: String)
    {
        this.fullname = fullName
    }
    fun getEmail():String{
        return email
    }

    fun setEmail(email: String){
        this.email = email
    }

}