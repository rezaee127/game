package com.example.game

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.game.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var btnArray=ArrayList<Button>()
    var a=0
    var b=0
    var mode=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnArray = arrayListOf(binding.answer1Btn,binding.answer2Btn
            ,binding.answer3Btn,binding.answer4Btn)

        dice()

        binding.diceBtn.setOnClickListener {
            Storage.questionNumber++
            for (button in btnArray){
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
            }
            enableButton()
            dice()
        }
    }




    fun setTextButton(number:Int){
        for (i in btnArray.indices){
                if (number == i){
                    btnArray[i].text =mode.toString()
                }else{
                    btnArray[i].text=Storage.getRandom().toString()
                }

        }
    }


    fun correctAnswer(button: Button){
       if(button.text==mode.toString()){
           Toast.makeText(this,"correct",Toast.LENGTH_SHORT).show()
           Storage.score+=5
           binding.scoreTxv.text=Storage.score.toString()
           button.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
           disableButton()
       } else{
           Toast.makeText(this,"incorrect",Toast.LENGTH_SHORT).show()
           Storage.score-=2
           binding.scoreTxv.text=Storage.score.toString()
           button.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
           disableButton()
       }

    }

    private fun disableButton() {
       for (button in btnArray){
           button.isEnabled=false
       }
    }

    private fun enableButton() {
        for (button in btnArray){
            button.isEnabled=true
        }
    }

    private fun dice() {
         a=Storage.randomNumberA()
         b=Storage.randomNumberB()
         mode=a%b
        binding.aNumberTxv.text = a.toString()
        binding.bNumberTxv.text = b.toString()
        binding.scoreTxv.text=Storage.score.toString()
        val numRandom = (0..3).random()
        setTextButton(numRandom)

        for (button in btnArray){
            button.setOnClickListener {  }
        }

        binding.answer1Btn.setOnClickListener {
            correctAnswer(binding.answer1Btn)
        }
        binding.answer2Btn.setOnClickListener {
            correctAnswer(binding.answer2Btn)
        }
        binding.answer3Btn.setOnClickListener {
            correctAnswer(binding.answer3Btn)
        }
        binding.answer4Btn.setOnClickListener {
            correctAnswer(binding.answer4Btn)
        }
    }
}