package com.blood.ease.learning.model

class Question {
    private var question : String = ""
    private var option1 : String = ""
    private var option2 : String = ""
    private var option3 : String = ""
    private var option4 : String = ""
    private var answer : String = ""

    constructor()
    constructor(
        question: String,
        option1: String,
        option2: String,
        option3: String,
        option4: String,
        answer: String
    ) {
        this.question = question
        this.option1 = option1
        this.option2 = option2
        this.option3 = option3
        this.option4 = option4
        this.answer = answer
    }

    fun setQuestion(question: String){
        this.question = question
    }
    fun getQuestion():String{
        return question
    }

    fun setOption1(option1: String){
        this.option1 = option1
    }
    fun getOption1():String{
        return option1
    }

    fun setOption2(option2: String){
        this.option2 = option2
    }
    fun getOption2():String{
        return option2
    }

    fun setOption3(option3: String){
        this.option3 = option3
    }
    fun getOption3():String{
        return option3
    }

    fun setOption4(option4: String){
        this.option4 = option4
    }
    fun getOption4():String{
        return option4
    }

    fun setAnswer(answer: String){
        this.answer = answer
    }
    fun getAnswer():String{
        return answer
    }

}