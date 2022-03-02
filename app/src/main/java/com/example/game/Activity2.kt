package com.example.game

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.game.databinding.Activity2Binding
import com.example.game.databinding.ActivityMainBinding
import com.google.android.material.internal.ContextUtils.getActivity

class Activity2 : AppCompatActivity() {
    lateinit var binding:Activity2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_2)

        binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
       binding.scoreFinalTxv.text="your score is  ${Storage.score.toString()}"
       binding.record .text="most score is  ${Storage.maxScore.toString()}"

        binding.exitBtn.setOnClickListener {
            Storage.questionNumber=1
            Storage.score=0

            exitApplication()
        }


        binding.replyBtn.setOnClickListener {
            Storage.questionNumber=1
            Storage.score=0
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
    @SuppressLint("RestrictedApi")
    fun exitApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getActivity(this)?.finishAffinity()
        } else{
            getActivity(this)?.finish()
            System.exit(0)
        }
    }
}