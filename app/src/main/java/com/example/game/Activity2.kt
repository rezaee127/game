package com.example.game

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.game.databinding.Activity2Binding
import com.google.android.material.internal.ContextUtils.getActivity

class Activity2 : AppCompatActivity() {
    lateinit var binding:Activity2Binding
    lateinit var pref: SharedPreferences
    lateinit var edit: SharedPreferences.Editor
    var max=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_2)
        binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        saveOnSharedPreferences()
    }

    @SuppressLint("SetTextI18n", "RestrictedApi")
    private fun initView() {
        saveOnSharedPreferences()
        if (Storage.count==0){
            saveOnSharedPreferences()
            Storage.count++
        }

        if (!max.isNullOrBlank()){
            if (max >Storage.maxScore.toString()){
                Storage.maxScore=max.toInt()
                saveOnSharedPreferences()
            }
        }

       binding.scoreFinalTxv.text="your score is  ${Storage.score}"
       binding.record .text="most score is  ${Storage.maxScore}"

        binding.exitBtn.setOnClickListener {
            Storage.questionNumber=1
            Storage.score=0
           // saveOnSharedPreferences()
            getActivity(this)?.finishAffinity()
        }


        binding.replyBtn.setOnClickListener {
            Storage.questionNumber=1
            Storage.score=0
            val intent=Intent(this,Activity1::class.java)
            startActivity(intent)
        }

    }

    private fun saveOnSharedPreferences(){
        pref = getSharedPreferences("shared",MODE_PRIVATE)
        edit = pref.edit()
        edit.putString("maxScore", Storage.maxScore.toString())
        edit.apply()
    }

    private fun getMaxScoreFromShared() :String{
        max= pref.getString("maxScore","").toString()
        return max
    }


}