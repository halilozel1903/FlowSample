package com.halil.ozel.flowsample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
        onEachViewModel()
        getLastValueViewModel()
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

    private fun onEachViewModel() {
        countDownTimerFlow.onEach {
            println(it)
        }.launchIn(viewModelScope)
    }

    private fun getLastValueViewModel() {
        viewModelScope.launch {
            countDownTimerFlow.collectLatest {
                delay(2000)
                println("counter is : $it")
            }
        }
    }

    // LiveData vs StateFlow vs SharedFlow

    private val  _liveData = MutableLiveData("Kotlin LiveData")
    val liveData: LiveData<String> = _liveData

    fun changeLiveDataValue() {
        _liveData.value = "Live Data"
    }

    private val  _stateFlow = MutableStateFlow("Kotlin StateFlow")
    val stateFlow = _stateFlow.asStateFlow()

    fun changeStateFlowValue() {
        _stateFlow.value = "State Flow"
    }

    // SharedFlow is highly configurable version of stateFlow.
    private val  _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun changeSharedFlowValue() {
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }
}