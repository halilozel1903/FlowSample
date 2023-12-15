package com.halil.ozel.flowsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by Halil Ozel on 15.12.2023.
 */
class FlowViewModel : ViewModel() {
    val countDownTimerFlow = flow {
        val countDownFrom = 13
        var counter = countDownFrom
        emit(countDownFrom)
        while (counter > 0) {
            delay(1000)
            counter--
            emit(counter)
        }
    }

    init {
        collectInViewModel()
    }

    private fun collectInViewModel() {
        viewModelScope.launch {
            countDownTimerFlow.filter {
                it % 4 == 0
            }
                .map {
                    it * it
                }
                .collect {
                    println("counter is : $it")
                }
        }
    }
}