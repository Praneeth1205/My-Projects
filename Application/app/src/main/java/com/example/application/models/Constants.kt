package com.example.application.models

object Constants {
    fun getQuestions():ArrayList<Question>{
        val questionList = ArrayList<Question>()

        val q1 = Question(
            1,
            "Who invented the Light Bulb?",
            "Albert Einstein",
            "Thomas Alva Edison",
            "C.V.Raman",
            "None",
            2
        )

        questionList.add(q1)
        val q2 = Question(
            2,
            "What is the name of the biggest planet in our solar system?",
            "Jupiter",
            "Mars",
            "Earth",
            "Mercury",
            1
        )

        questionList.add(q2)

        val q3 = Question(
            3,
            "Who discovered Penicillin?",
            "Albert Einstein",
            "Alexander Fleming",
            "Alexander Graham Bell",
            "Johannes Gutenberg",
            2
        )

        questionList.add(q3)

        val q4 = Question(
            4,
            "Which planet in our solar system is known as the Blue Planet? ",
            "Jupiter",
            "Mars",
            "Mercury",
            "Earth",
            4
        )

        questionList.add(q4)

        val q5 = Question(
            5,
            "Who won the Best Footballer 2016 in the World?",
            "Cristiano Ronaldo",
            "Cristiano Robert",
            "Robert Paul",
            "Mark Admin",
            1
        )

        questionList.add(q5)

        return questionList

    }
}