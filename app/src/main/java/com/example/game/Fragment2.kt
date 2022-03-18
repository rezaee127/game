package com.example.game

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.game.databinding.Fragment2Binding
import com.google.android.material.internal.ContextUtils


class Fragment2 : Fragment() {
    lateinit var binding: Fragment2Binding
    lateinit var edit: SharedPreferences.Editor
    var max=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment2Binding.inflate (inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            ContextUtils.getActivity(this.context)?.finishAffinity()
        }


        binding.replyBtn.setOnClickListener {
            Storage.questionNumber=1
            Storage.score=0
            findNavController().navigate(R.id.action_fragment2_to_fragment1)
        }

    }

    private fun saveOnSharedPreferences(){
        val pref = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
        edit = pref.edit()
        edit.putString("maxScore", Storage.maxScore.toString())
        edit.apply()
    }

    private fun getMaxScoreFromShared() :String{
        val pref = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
        max= pref.getString("maxScore","").toString()
        return max
    }


}