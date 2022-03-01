package com.example.game

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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

        binding.diceBtn.setOnClickListener { dice() }
    }

  //  private fun dice() {



       /* val numRandom = (1..4).random()
        correctAnswer(numRandom)
        when (numRandom){
            1 ->{ binding.answer1Btn.text =Storage.answer().toString()
                binding.answer2Btn.text = Storage.getRandom().toString()
                binding.answer3Btn.text = Storage.getRandom().toString()
                binding.answer4Btn.text =Storage.getRandom().toString()
            }
            2 ->{ binding.answer2Btn.text =Storage.answer().toString()
                binding.answer1Btn.text = Storage.getRandom().toString()
                binding.answer3Btn.text = Storage.getRandom().toString()
                binding.answer4Btn.text =Storage.getRandom().toString()
            }
            3 ->{ binding.answer3Btn.text =Storage.answer().toString()
                binding.answer2Btn.text = Storage.getRandom().toString()
                binding.answer1Btn.text = Storage.getRandom().toString()
                binding.answer4Btn.text =Storage.getRandom().toString()
            }
            4 ->{ binding.answer4Btn.text =Storage.answer().toString()
                binding.answer2Btn.text = Storage.getRandom().toString()
                binding.answer3Btn.text = Storage.getRandom().toString()
                binding.answer1Btn.text =Storage.getRandom().toString()
            }
        }*/
//    }


    fun setTextButton(number:Int){
        for (i in btnArray.indices){
                if (number == i){
                    btnArray[i].text =mode.toString()
                }else{
                    btnArray[i].text=Storage.getRandom().toString()
                }

        }
    }


   // @SuppressLint("ResourceAsColor")
    fun correctAnswer(number:Int){


      //  for (i in btnArray.indices){





               /* if (number == i){
                  //  btnArray[i].setBackgroundColor(R.color.green)
                    Toast.makeText(this,"correct",Toast.LENGTH_SHORT).show()
                    break
                }else{
                   // btnArray[i].setBackgroundColor(R.color.red)
                    Toast.makeText(this,"incorrect",Toast.LENGTH_SHORT).show()
                    break
                }
*/
    }

    private fun dice() {
         a=Storage.randomNumberA()
         b=Storage.randomNumberB()
         mode=a%b
        binding.aNumberTxv.text = a.toString()
        binding.bNumberTxv.text = b.toString()
        val numRandom = (0..3).random()
        setTextButton(numRandom)

            binding.answer1Btn.setOnClickListener {
               if(binding.answer1Btn.text==mode.toString()){
                   Toast.makeText(this,"correct",Toast.LENGTH_SHORT).show()
               } else{
                   Toast.makeText(this,"incorrect",Toast.LENGTH_SHORT).show()
               }
        }
        binding.answer2Btn.setOnClickListener {
            if(binding.answer2Btn.text==mode.toString()){
                Toast.makeText(this,"correct",Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this,"incorrect",Toast.LENGTH_SHORT).show()
            }
        }
        binding.answer3Btn.setOnClickListener {
            if(binding.answer3Btn.text==mode.toString()){
                Toast.makeText(this,"correct",Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this,"incorrect",Toast.LENGTH_SHORT).show()
            }
        }
        binding.answer4Btn.setOnClickListener {
            if(binding.answer4Btn.text==mode.toString()){
                Toast.makeText(this,"correct",Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this,"incorrect",Toast.LENGTH_SHORT).show()
            }
        }
    }
}