package com.halil.ozel.flowsample

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/**
 * Created by Halil Ozel on 15.12.2023.
 */
class FlowViewModel : ViewModel() {
    val countDownTimerFlow = flow {
        val countDownFrom = 13
        var counter = countDownFrom
        emit(countDownFrom)
        while (counter > 0){
            delay(1000)
            counter --
            emit(counter)
        }
    }
}