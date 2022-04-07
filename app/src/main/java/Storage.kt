package com.example.game


object Storage {
    var timer=15000L
    var score=0
    var questionNumber=1
    var maxScore=0
    var operator=""
    var result=0L
    var a1=1L
    var a2=100L
    var b1=1L
    var b2=10L
    var a =-1L
    var b =-1L
    var arrayOfRandoms=ArrayList<Long>()


    fun randomNumberA():Long{
        a = (a1 .. a2).random()
        return a
    }

    fun randomNumberB():Long{
        b = (b1 .. b2).random()
        return b
    }

    fun getRandom() :Long {
        return when(operator){
            "+" -> (result-2 .. result+3).random()
            else -> (result-3 .. result+2).random()
        }
    }


}