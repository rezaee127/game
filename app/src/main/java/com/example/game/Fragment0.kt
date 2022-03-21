package com.example.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.game.databinding.Fragment0Binding


class Fragment0 : Fragment() {
    lateinit var  binding : Fragment0Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment0Binding.inflate (inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_0, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        val arrayOfOperatorRadioButtons= arrayOf(binding.radioButton1,binding.radioButton2,
            binding.radioButton3,binding.radioButton4,binding.radioButton5)

        val arrayOfTimeRadioButtons= arrayOf(binding.radioButton6,binding.radioButton7,
            binding.radioButton8,binding.radioButton9,binding.radioButton10)

        binding.radioButton7.isChecked=true

        binding.button.setOnClickListener {

            if (!binding.radioButton1.isChecked && !binding.radioButton2.isChecked &&
                !binding.radioButton3.isChecked && !binding.radioButton4.isChecked &&
                !binding.radioButton5.isChecked){
                binding.radioButton1.error="یک عملگر انتخاب کنید"
            }else {
                for (i in arrayOfOperatorRadioButtons.indices) {
                    if (arrayOfOperatorRadioButtons[i].isChecked) {
                        Storage.operator = arrayOfOperatorRadioButtons[i].text.toString()
                    }
                }

                for (i in arrayOfTimeRadioButtons.indices) {
                    if (arrayOfTimeRadioButtons[i].isChecked) {
                        when(i){
                            0->Storage.timer=10000
                            1->Storage.timer=15000
                            2->Storage.timer=20000
                            3->Storage.timer=25000
                            4->Storage.timer=30000
                        }
                    }
                }


                if (!binding.editTextA1.text.isNullOrBlank())
                    Storage.a1 = binding.editTextA1.text.toString().toInt()
                if (!binding.editTextA2.text.isNullOrBlank())
                    Storage.a2 = binding.editTextA2.text.toString().toInt()
                if(Storage.a1>Storage.a2){
                    var x=Storage.a2
                    Storage.a2=Storage.a1
                    Storage.a1=x
                }
                if (!binding.editTextB1.text.isNullOrBlank())
                    Storage.b1 = binding.editTextB1.text.toString().toInt()
                if (!binding.editTextB2.text.isNullOrBlank())
                    Storage.b2 = binding.editTextB2.text.toString().toInt()
                if(Storage.b1>Storage.b2){
                    var x=Storage.b2
                    Storage.b2=Storage.b1
                    Storage.b1=x
                }

                findNavController().navigate(R.id.action_fragment0_to_fragment1)
            }
        }

    }


}