package com.example.game

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.game.databinding.Fragment1Binding


class Fragment1 : Fragment() {
    lateinit var binding: Fragment1Binding
    var btnArray=ArrayList<Button>()
    var arrayOfRandoms=ArrayList<Int>()
    var flagDice=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment1Binding.inflate (inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState!= null){
            binding.aNumberTxv .text = savedInstanceState.getString("A")
            binding.bNumberTxv .text = savedInstanceState.getString("B")
            binding.textViewComment.text = savedInstanceState.getString("Text")
            binding.answer1Btn .text = savedInstanceState.getString("Button1")
            binding.answer2Btn .text = savedInstanceState.getString("Button2")
            binding.answer3Btn .text = savedInstanceState.getString("Button3")
            binding.answer4Btn .text = savedInstanceState.getString("Button4")
            binding.answer1Btn.isEnabled = savedInstanceState.getBoolean("isEnabled")
            binding.answer2Btn.isEnabled = savedInstanceState.getBoolean("isEnabled")
            binding.answer3Btn.isEnabled = savedInstanceState.getBoolean("isEnabled")
            binding.answer4Btn.isEnabled = savedInstanceState.getBoolean("isEnabled")
            binding.scoreTxv.text= savedInstanceState.getString("Score")
            Storage.questionNumber= savedInstanceState.getInt("questionNumber")
        }

        val pref = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
        if (!(pref.getString("maxScore","")).isNullOrBlank()) {
            Storage.maxScore = pref.getString("maxScore", "").toString().toInt()
        }

        btnArray = arrayListOf(binding.answer1Btn,binding.answer2Btn
            ,binding.answer3Btn,binding.answer4Btn)


        dice()

        binding.diceBtn.setOnClickListener {

            Storage.questionNumber++
            if (Storage.questionNumber>=6){
                if (Storage.maxScore<Storage.score){
                    Storage.maxScore=Storage.score
                }
                Storage.questionNumber=1
                findNavController().navigate(R.id.action_fragment1_to_fragment2)
            }
            else {
                for (button in btnArray) {
                    button.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.purple_500))
                }
                enableButton()
                flagDice=true
                dice()

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("A" , binding.aNumberTxv .text.toString())
        outState.putString("B" , binding.bNumberTxv .text.toString())
        outState.putString("Text" , binding.textViewComment.text.toString())
        outState.putString("Button1" , binding.answer1Btn .text.toString())
        outState.putString("Button2" , binding.answer2Btn .text.toString())
        outState.putString("Button3" , binding.answer3Btn .text.toString())
        outState.putString("Button4" , binding.answer4Btn .text.toString())
        outState.putString("Score" , binding.scoreTxv.text.toString())
        outState.putInt("questionNumber" ,Storage.questionNumber)
        outState.putBoolean("isEnabled" , binding.answer4Btn.isEnabled)
        super.onSaveInstanceState(outState)

    }




    fun setTextButton(number:Int){
        for (i in btnArray.indices){
            if (number == i){
                btnArray[i].text =Storage.result.toString()
            }else{
                var flag=true
                while(flag){
                    val rand=Storage.getRandom()
                    if(rand !in arrayOfRandoms){
                        btnArray[i].text=rand.toString()
                        arrayOfRandoms.add(rand)
                        flag=false
                    }
                }

            }

        }
    }


    fun correctAnswer(button: Button){
        if(button.text==Storage.result.toString()){
            Storage.score+=5
            binding.scoreTxv.text=Storage.score.toString()
            button.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.green))

            disableButton()
        } else{
            Storage.score-=2
            binding.scoreTxv.text=Storage.score.toString()
            button.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.red))
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

    fun dice() {
        if(binding.aNumberTxv.text.isBlank()||flagDice) {
            Storage.result = calculateResult()
            arrayOfRandoms.add(Storage.result)

            binding.aNumberTxv.text = Storage.a.toString()
            binding.bNumberTxv.text = Storage.b.toString()
            binding.scoreTxv.text = Storage.score.toString()
            when(Storage.operator){
                "+"-> binding.textViewComment.text="مجموع دو عدد بالا را حدس بزنید"
                "-"-> binding.textViewComment.text="نتیجه تفریق عدد پایینی از عدد بالایی کدام است؟"
                "*"-> binding.textViewComment.text="حاصل ضرب دو عدد بالا را حدس بزنید"
                "/"-> binding.textViewComment.text="خارج قسمت تقسیم عدد بالایی بر عدد پایینی کدام است؟"
                "%"-> binding.textViewComment.text="باقیمانده تقسیم عدد بالایی بر عدد پایینی کدام است؟"
            }
            val numRandom = (0..3).random()
            setTextButton(numRandom)
        }
        for (button in btnArray){
            button.setOnClickListener {
                correctAnswer(button)
            }
        }

    }

    private fun calculateResult():Int {
        Storage.a=Storage.randomNumberA()
        Storage.b=Storage.randomNumberB()
       return when (Storage.operator){
            "+" -> Storage.a+Storage.b
            "-" -> Storage.a-Storage.b
            "*" -> Storage.a*Storage.b
            "/" -> Storage.a/Storage.b
            else -> Storage.a%Storage.b
        }
    }




}
