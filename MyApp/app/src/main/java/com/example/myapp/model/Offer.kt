package com.example.myapp.model

class Offer
{
    private var offerImage : String = ""

    constructor()
    constructor(offerImage: String) {
        this.offerImage = offerImage
    }

    fun getOfferImage() : String
    {
        return offerImage
    }
    fun setOfferImage(offerImage: String)
    {
        this.offerImage = offerImage
    }

}
