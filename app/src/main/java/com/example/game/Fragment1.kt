package com.example.game

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.game.databinding.Fragment1Binding


class Fragment1 : Fragment()  {
    lateinit var binding: Fragment1Binding
    val vModel:Fragment1ViewModel by viewModels()
    var btnArray=ArrayList<Button>()



    var cTimer: CountDownTimer? = null
    fun startTimer() {
        cTimer = object : CountDownTimer(vModel.storage.timer, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.TimeTxv.text ="${millisUntilFinished / 1000}"
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                binding.TimeTxv.text = "0"
                if (vModel.count==0) {
                    vModel.storage.score -= 2
                }else
                    vModel.count--

                binding.scoreTxv.text=vModel.storage.score.toString()
                for (button in btnArray){
                    if(button.text==vModel.storage.result.toString()){
                        context?.let { ContextCompat.getColor(it, R.color.green) }
                            ?.let { button.setBackgroundColor(it) }
                    }
                }


                disableButton()
            }
        }.start()
    }

    fun cancelTimer() {
        cTimer?.cancel()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment1Binding.inflate (inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_1, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            view.findViewById<TextView>(R.id.aNumber_txv).text = vModel.aNumber
            view.findViewById<TextView>(R.id.bNumber_txv).text = vModel.bNumber
            view.findViewById<TextView>(R.id.text_view_comment).text = vModel.comment
            view.findViewById<Button>(R.id.answer1_btn).text = vModel.textOfAnswer1Btn
            view.findViewById<Button>(R.id.answer2_btn).text = vModel.textOfAnswer2Btn
            view.findViewById<Button>(R.id.answer3_btn).text = vModel.textOfAnswer3Btn
            view.findViewById<Button>(R.id.answer4_btn).text = vModel.textOfAnswer4Btn
            view.findViewById<Button>(R.id.answer1_btn).isEnabled = vModel.enable
            view.findViewById<Button>(R.id.answer2_btn).isEnabled = vModel.enable
            view.findViewById<Button>(R.id.answer3_btn).isEnabled = vModel.enable
            view.findViewById<Button>(R.id.answer4_btn).isEnabled = vModel.enable
            view.findViewById<TextView>(R.id.score_txv).text= vModel.textOfScoreTxv
            if(vModel.textOfScoreTxv!="")
                vModel.storage.score=vModel.textOfScoreTxv.toInt()


        val pref = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
        if (!(pref.getString("maxScore","")).isNullOrBlank()) {
            vModel.storage.maxScore = pref.getString("maxScore", "").toString().toInt()
        }

        btnArray = arrayListOf(binding.answer1Btn,binding.answer2Btn
            ,binding.answer3Btn,binding.answer4Btn)


        dice()

        binding.diceBtn.setOnClickListener {
            cancelTimer()
            vModel.storage.questionNumber++
            if (vModel.storage.questionNumber>=6){
                if (vModel.storage.maxScore<vModel.storage.score){
                    vModel.storage.maxScore=vModel.storage.score
                }
                vModel.storage.questionNumber=1
                findNavController().navigate(R.id.action_fragment1_to_fragment2)
            }
            else {
                for (button in btnArray) {
                    button.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.purple_501))
                }
                enableButton()
                vModel.flagDice=true
                vModel.count=0
                dice()

            }
        }


    }






    fun setTextButton(number:Int){
        for (i in btnArray.indices){
            if (number == i){
                btnArray[i].text =vModel.storage.result.toString()
            }else{
                var flag=true
                while(flag){
                    val rand=vModel.storage.getRandom()
                    if(rand !in vModel.storage.arrayOfRandoms){
                        btnArray[i].text=rand.toString()
                        vModel.storage.arrayOfRandoms.add(rand)
                        flag=false
                    }
                }

            }

        }
    }


    fun correctAnswer(button: Button){
        if(button.text==vModel.storage.result.toString()){
            vModel.storage.score+=5
            binding.scoreTxv.text=vModel.storage.score.toString()
            button.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.green))
            disableButton()
        } else{
            vModel.storage.score-=2
            binding.scoreTxv.text=vModel.storage.score.toString()
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
        startTimer()
        if(binding.aNumberTxv.text.isBlank()|| vModel.flagDice) {
            vModel.storage.arrayOfRandoms.clear()
            vModel.storage.result = calculateResult()
            vModel.storage.arrayOfRandoms.add(vModel.storage.result)

            binding.aNumberTxv.text = vModel.storage.a.toString()
            binding.bNumberTxv.text = vModel.storage.b.toString()
            binding.scoreTxv.text = vModel.storage.score.toString()
            when(vModel.storage.operator){
                "+"-> binding.textViewComment.text="مجموع دو عدد بالا را حدس بزنید"
                "-"-> binding.textViewComment.text="نتیجه تفریق عدد پایینی از عدد بالایی کدام است؟"
                "*"-> binding.textViewComment.text="حاصل ضرب دو عدد بالا را حدس بزنید"
                "/"-> binding.textViewComment.text="خارج قسمت تقسیم عدد بالایی بر عدد پایینی کدام است؟"
                "%"-> binding.textViewComment.text="باقیمانده تقسیم عدد بالایی بر عدد پایینی کدام است؟"
            }
            val numRandom = (0..3).random()
            setTextButton(numRandom)
            vModel.flagDice=false
        }
        for (button in btnArray){
            button.setOnClickListener {
                cancelTimer()
                correctAnswer(button)
                for (button in btnArray){
                    if(button.text==vModel.storage.result.toString()){
                        button.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.green))
                    }
                }
            }
        }

    }

    private fun calculateResult():Int {
        vModel.storage.a=vModel.storage.randomNumberA()
        vModel.storage.b=vModel.storage.randomNumberB()
       return when (vModel.storage.operator){
            "+" -> vModel.storage.a+vModel.storage.b
            "-" -> vModel.storage.a-vModel.storage.b
            "*" -> vModel.storage.a*vModel.storage.b
            "/" -> vModel.storage.a/vModel.storage.b
            else -> vModel.storage.a%vModel.storage.b
        }
    }


     @SuppressLint("CutPasteId")
     fun saveOnViewModel() {
        vModel.aNumber=view?.findViewById<TextView>(R.id.aNumber_txv)?.text.toString()
        vModel.bNumber=view?.findViewById<TextView>(R.id.bNumber_txv)?.text.toString()
        vModel.comment=view?.findViewById<TextView>(R.id.text_view_comment)?.text.toString()
        vModel.textOfAnswer1Btn=view?.findViewById<Button>(R.id.answer1_btn)?.text.toString()
        vModel.textOfAnswer2Btn=view?.findViewById<Button>(R.id.answer2_btn)?.text.toString()
        vModel.textOfAnswer3Btn=view?.findViewById<Button>(R.id.answer3_btn)?.text.toString()
        vModel.textOfAnswer4Btn=view?.findViewById<Button>(R.id.answer4_btn)?.text.toString()
        vModel.textOfScoreTxv=view?.findViewById<TextView>(R.id.score_txv)?.text.toString()
        vModel.enable= view?.findViewById<Button>(R.id.answer4_btn)?.isEnabled == true
        vModel.count++
    }

    override fun onStop() {
        super.onStop()
        saveOnViewModel()
    }
}

