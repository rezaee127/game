package com.example.game

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.game.databinding.Fragment1Binding
import com.example.game.databinding.Fragment2Binding

class Fragment1 : Fragment() {
    lateinit var binding: Fragment1Binding
    lateinit var pref: SharedPreferences
    var btnArray=ArrayList<Button>()
    var a=0
    var b=0
    var mode=-1
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
            binding.answer1Btn .text = savedInstanceState.getString("Button1")
            binding.answer2Btn .text = savedInstanceState.getString("Button2")
            binding.answer3Btn .text = savedInstanceState.getString("Button3")
            binding.answer4Btn .text = savedInstanceState.getString("Button4")
            binding.answer1Btn.isClickable = savedInstanceState.getBoolean("isClickable")
            binding.answer2Btn.isClickable = savedInstanceState.getBoolean("isClickable")
            binding.answer3Btn.isClickable = savedInstanceState.getBoolean("isClickable")
            binding.answer4Btn.isClickable = savedInstanceState.getBoolean("isClickable")
            binding.scoreTxv.text= savedInstanceState.getString("Score")
            Storage.questionNumber= savedInstanceState.getInt("questionNumber")
        }
        val pref = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
        if (!(pref.getString("maxScore","")).isNullOrBlank()) {
            Storage.maxScore = pref.getString("maxScore", "").toString().toInt()
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
                findNavController().navigate(R.id.action_fragment1_to_fragment2)
            }
            else {
                for (button in btnArray) {
                    button.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.purple_500))
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
        outState.putBoolean("isClickable" , binding.answer4Btn.isClickable)

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
            //Toast.makeText(this,"correct",Toast.LENGTH_SHORT).show()
            Storage.score+=5
            binding.scoreTxv.text=Storage.score.toString()
            button.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.green))

            disableButton()
        } else{
            //Toast.makeText(this,"incorrect",Toast.LENGTH_SHORT).show()
            Storage.score-=2
            binding.scoreTxv.text=Storage.score.toString()
            button.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.red))
            disableButton()
        }

    }

    private fun disableButton() {
        for (button in btnArray){
            button.isClickable=false
        }
    }

    private fun enableButton() {
        for (button in btnArray){
            button.isClickable=true
        }
    }

    fun dice() {

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
