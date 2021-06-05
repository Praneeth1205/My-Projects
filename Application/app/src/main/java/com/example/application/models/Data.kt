package com.example.application.models

class Data {
    private var imageUrl : String =""
    private var imageUrlTwo : String =""
    private var quizTitle : String = ""
    private var prizeCoins : String = ""
    private var userCount : String = ""
    private var entryFee : String = ""
    private var participantOne : String = ""
    private var participantTwo : String = ""

    constructor()
    constructor(
        imageUrl: String,
        quizTitle: String,
        prizeCoins: String,
        userCount: String,
        entryFee: String,
        imageUrlTwo :String,
        participantOne:String,
        participantTwo:String

    ) {
        this.imageUrl = imageUrl
        this.quizTitle = quizTitle
        this.prizeCoins = prizeCoins
        this.userCount = userCount
        this.entryFee = entryFee
        this.imageUrlTwo = imageUrlTwo
        this.participantOne = participantOne
        this.participantTwo = participantTwo
    }


    fun getImageUrl():String{
        return imageUrl
    }
    fun setImageUrl(imageUrl: String){
        this.imageUrl = imageUrl
    }

    fun getImageUrlTwo():String{
        return imageUrlTwo
    }
    fun setImageUrlTwo(imageUrlTwo: String){
        this.imageUrlTwo = imageUrlTwo
    }

    fun getQuizTitle():String{
        return quizTitle
    }
    fun setQuizTitle(quizTitle: String){
        this.quizTitle = quizTitle
    }

    fun getPrizeCoins():String{
        return prizeCoins
    }
    fun setPrizeCoins(prizeCoins: String){
        this.prizeCoins = prizeCoins
    }

    fun getUserCount():String{
        return userCount
    }
    fun setUserCount(userCount: String){
        this.userCount = userCount
    }

    fun getEntryFee():String{
        return entryFee
    }
    fun setEntryFee(entryFee: String){
        this.entryFee = entryFee
    }

    fun getParticipantOne():String{
        return participantOne
    }
    fun setParticipantOne(participantOne:String){
        this.participantOne = participantOne
    }

    fun getParticipantTwo():String{
        return participantTwo
    }
    fun setParticipantTwo(participantTwo:String){
        this.participantTwo = participantTwo
    }

}