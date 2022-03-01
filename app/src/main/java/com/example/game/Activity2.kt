package com.example.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.game.databinding.Activity2Binding
import com.example.game.databinding.ActivityMainBinding

class Activity2 : AppCompatActivity() {
    lateinit var binding:Activity2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_2)
        binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
       binding.scoreFinalTxv.text=Storage.score.toString()

        binding.exitBtn.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("LOGOUT", true)
            startActivity(intent)

        }

    }
}