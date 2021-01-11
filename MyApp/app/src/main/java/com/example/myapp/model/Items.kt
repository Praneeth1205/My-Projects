package com.example.myapp.model

class Items {
    private var itemname : String = ""
    private var itemimage : String = ""
    private var mrp : String = ""
    private var id : String = ""
    private var uid : String = ""

    constructor()
    constructor(itemname: String, itemimage: String, mrp: String,id : String,uid : String) {
        this.itemname = itemname
        this.itemimage = itemimage
        this.mrp = mrp
        this.id = id
        this.uid = uid

    }

    fun getItemName() : String
    {
        return itemname
    }
    fun getItemImage() : String
    {
        return itemimage
    }
    fun getMRP() : String
    {
        return mrp
    }
    fun getId() : String
    {
        return id
    }
    fun getUID() : String
    {
        return uid
    }
    fun setItemName(itemname: String)
    {
        this.itemname = itemname
    }
    fun setItemImage(itemimage: String)
    {
        this.itemimage = itemimage
    }
    fun setMRP(mrp: String)
    {
        this.mrp = mrp
    }
    fun setId(id: String)
    {
        this.id = id
    }
    fun setUID(uid: String)
    {
        this.uid = uid
    }
}