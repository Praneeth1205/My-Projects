package com.example.myapp.model

class Orders
{
private var nameofitem : String = ""
private var mrpofitem : String = ""
private var imageofitem : String = ""
private var id : String = ""

    constructor()
    constructor(nameofitem: String, mrpofitem: String, imageofitem: String,id : String) {
        this.nameofitem = nameofitem
        this.mrpofitem = mrpofitem
        this.imageofitem = imageofitem
        this.id = id
    }

    fun getNameOFItem() : String
    {
        return nameofitem
    }
    fun getMRPOfItem() : String
    {
        return mrpofitem
    }
    fun getImageOfItem() : String
    {
        return imageofitem
    }
    fun getId() : String
    {
        return id
    }
    fun setNameOFItem(nameofitem: String)
    {
        this.nameofitem = nameofitem
    }
    fun setMRPOfItem(mrpofitem: String)
    {
        this.mrpofitem = mrpofitem
    }
    fun setImageOfItem(imageofitem: String)
    {
        this.imageofitem = imageofitem
    }
    fun setId(id: String)
    {
        this.id = id
    }


}