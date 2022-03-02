package com.example.game

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
        if(savedInstanceState!= null){
            binding.aNumberTxv .text = savedInstanceState.getString("A")
            binding.bNumberTxv .text = savedInstanceState.getString("B")
            binding.answer1Btn .text = savedInstanceState.getString("Button1")
            binding.answer2Btn .text = savedInstanceState.getString("Button2")
            binding.answer3Btn .text = savedInstanceState.getString("Button3")
            binding.answer4Btn .text = savedInstanceState.getString("Button4")
            binding.answer4Btn .text = savedInstanceState.getString("Button4")
            binding.answer1Btn.isEnabled = savedInstanceState.getBoolean("Enable")
            binding.answer2Btn.isEnabled = savedInstanceState.getBoolean("Enable")
            binding.answer3Btn.isEnabled = savedInstanceState.getBoolean("Enable")
            binding.answer4Btn.isEnabled = savedInstanceState.getBoolean("Enable")
            binding.scoreTxv.text= savedInstanceState.getString("Score")
            Storage.questionNumber= savedInstanceState.getInt("questionNumber")
        }
        if (getIntent().getBooleanExtra("LOGOUT", false)) {
            finish()
        }



        btnArray = arrayListOf(binding.answer1Btn,binding.answer2Btn
            ,binding.answer3Btn,binding.answer4Btn)
        if(binding.aNumberTxv .text.isBlank()) {
            dice()
        }
        binding.diceBtn.setOnClickListener {
            Storage.questionNumber++
            if (Storage.questionNumber>=6){
                if (Storage.maxScore<Storage.score){
                    Storage.maxScore=Storage.score
                }
                Storage.questionNumber=1
                val intent= Intent(this,Activity2::class.java)
                startActivity(intent)
            }
            else {
                for (button in btnArray) {
                    button.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
                }
                enableButton()
                dice()
            }
        }
    }



    override fun onSaveInstanceState(outState: Bundle) {
    outState.putString("A" , binding.aNumberTxv .text.toString())
    outState.putString("B" , binding.bNumberTxv .text.toString())
    outState.putString("Button1" , binding.answer1Btn .text.toString())
    outState.putString("Button2" , binding.answer2Btn .text.toString())
    outState.putString("Button3" , binding.answer3Btn .text.toString())
    outState.putString("Button4" , binding.answer4Btn .text.toString())
    outState.putString("Score" , binding.scoreTxv.text.toString())
    outState.putInt("questionNumber" ,Storage.questionNumber)
    outState.putBoolean("Enable" , binding.answer4Btn.isEnabled)
        for (button in btnArray){
            var s=button.isEnabled
            var x =button.background
        }
    super.onSaveInstanceState(outState)
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