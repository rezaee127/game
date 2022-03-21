package com.example.game

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.fragment.findNavController
import com.example.game.databinding.Fragment2Binding
import com.google.android.material.internal.ContextUtils
import kotlin.math.abs


class Fragment2 : Fragment() {
    lateinit var binding: Fragment2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment2Binding.inflate (inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    @SuppressLint("SetTextI18n", "RestrictedApi")
    private fun initView() {

        val pref = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
        if(pref.getString("maxScore","").isNullOrBlank()){
            saveOnSharedPreferences()
        }else {
            if( pref.getString("maxScore","").toString().toInt()>Storage.maxScore){
                Storage.maxScore=pref.getString("maxScore","").toString().toInt()
            }else {
                saveOnSharedPreferences()
            }
        }


        if (Storage.score== abs(Storage.score))
            binding.scoreFinalTxv.text="امتیاز شما : ${Storage.score}"
        else
            binding.scoreFinalTxv.text="امتیاز شما : " + abs(Storage.score) + "-"
        binding.record .text="رکورد بازی : ${Storage.maxScore}"


        binding.exitBtn.setOnClickListener {
            Storage.questionNumber=1
            Storage.score=0
            requireActivity().finishAffinity()
        }


        binding.replyBtn.setOnClickListener {
            Storage.questionNumber=1
            Storage.score=0
            findNavController().navigate(R.id.action_fragment2_to_fragment1)
        }


        binding.buttonGoToFragment0.setOnClickListener {
            Storage.questionNumber=1
            Storage.score=0
            findNavController().navigate(R.id.action_fragment2_to_fragment0)
        }

    }

    private fun saveOnSharedPreferences(){
        val pref = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
        val edit = pref.edit()
        edit.putString("maxScore", Storage.maxScore.toString())
        edit.apply()
    }


}