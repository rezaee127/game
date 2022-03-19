package com.example.game

import android.os.CountDownTimer

fun time(){
    object  : CountDownTimer(15000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            Fragment1(). binding.TimeTxv.text = "" + millisUntilFinished / 1000
        }

        override fun onFinish() {
            //  binding.TimeTxv.setText("done!")
            Fragment1().dice()
        }
    }.start()

}
