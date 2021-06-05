package com.example.application.models

class Winner {
    private var imageUrlOne : String = ""
    private var imageUrlTwo : String = ""
    private var imageUrlThree : String = ""
    private var quizName : String = ""
    private var winningCoins : String = ""
    private var winningCount : String = ""

    constructor()
    constructor(
        imageUrlOne: String,
        imageUrlTwo: String,
        imageUrlThree: String,
        quizName: String,
        winningCoins: String,
        winningCount: String
    ) {
        this.imageUrlOne = imageUrlOne
        this.imageUrlTwo = imageUrlTwo
        this.imageUrlThree = imageUrlThree
        this.quizName = quizName
        this.winningCoins = winningCoins
        this.winningCount = winningCount
    }

    fun getImageUrlOne():String{
        return imageUrlOne
    }
    fun setImageUrlOne(imageUrlOne: String){
        this.imageUrlOne = imageUrlOne
    }

    fun getImageUrlTwo():String{
        return imageUrlTwo
    }
    fun setImageUrlTwo(imageUrlTwo: String){
        this.imageUrlTwo = imageUrlTwo
    }

    fun getImageUrlThree():String{
        return imageUrlThree
    }
    fun setImageUrlThree(imageUrlThree: String){
        this.imageUrlThree = imageUrlThree
    }


    fun getQuizName():String{
        return quizName
    }
    fun setQuizName(quizName: String){
        this.quizName = quizName
    }


    fun getWinningCoins():String{
        return winningCoins
    }
    fun setWinningCoins(winningCoins: String){
        this.winningCoins = winningCoins
    }


    fun getWinningCount():String{
        return winningCount
    }
    fun setWinningCount(winningCount: String){
        this.winningCount = winningCount
    }
}