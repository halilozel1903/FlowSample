package com.halil.ozel.flowsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halil.ozel.flowsample.ui.theme.FlowSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowSampleTheme {
                val flowViewModel: FlowViewModel by viewModels()
                SecondScreen(flowViewModel = flowViewModel)
            }
        }
    }

    @Composable
    fun FirstScreen(flowViewModel: FlowViewModel) {
        val counter = flowViewModel.countDownTimerFlow.collectAsState(initial = 13)
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = counter.value.toString(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

    @Composable
    fun SecondScreen(flowViewModel: FlowViewModel) {

        val liveDataValue = flowViewModel.liveData.observeAsState()

        val stateFlowValue = flowViewModel.stateFlow.collectAsState()

        val sharedFlowValue = flowViewModel.sharedFlow.collectAsState(initial = "")

        Surface(color = MaterialTheme.colorScheme.background) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = liveDataValue.value ?: "",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Button(onClick = {
                        flowViewModel.changeLiveDataValue()
                    }) {
                        Text(text = "LiveData Button")
                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(
                        text = stateFlowValue.value ?: "",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Button(onClick = {
                        flowViewModel.changeStateFlowValue()
                    }) {
                        Text(text = "State Flow Button")
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(
                        text = sharedFlowValue.value,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Button(onClick = {
                        flowViewModel.changeSharedFlowValue()

                    }) {
                        Text(text = "Shared Flow Button")
                    }
                }
            }
        }
    }
}
