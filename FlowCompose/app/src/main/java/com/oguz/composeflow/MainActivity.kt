package com.oguz.composeflow

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oguz.composeflow.ui.theme.ComposeFlowTheme

/**
 * Kotlin Flows - > LiveData yerine kullanılıyor
 * Coroutine içerisinde çalışıyor.
 * Multiple deger yayabilen yapılar -> emit
 * suspend fun just return only a single value but flow kullanırsan birden fazla deger return edebilirsin.
 * you can use a flow to receive live updates from a database.
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeFlowTheme {

                val viewmodel : MyViewModel by viewModels()

                //FirstScreen(viewModel = viewmodel)
                
                SecondScreen(viewModel = viewmodel)

                
            }

        }
    }
}

@Composable
fun FirstScreen(viewModel: MyViewModel) {
//TODO: collectAsState -> xml ile çalısıyor olsaydık collect derdik ve kod blogu ıcınde verılırdı
    // TODO:compose da oldugumuz ıcın collectAsState kullandık burda bir karısıklık oldugunda state konusu nedeniyle
    //TODO:  recomposion a tabi tutmamız gerekiyor
    //TODO: collectAsState -> Degeri alıyor ve flow dan state e ceviriyor.
    val counter = viewModel.countDownTimerFlow.collectAsState(initial = 10)

    Surface (color = MaterialTheme.colorScheme.background){
        Box(modifier = Modifier.fillMaxSize()){
            Text(text = counter.value.toString() ,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}


@Composable
fun SecondScreen(viewModel : MyViewModel) {

    val liveDataValue = viewModel.liveData.observeAsState()
    val stateFlowValue = viewModel.stateFlow.collectAsState()
    val sharedFlowValue = viewModel.sharedFlow.collectAsState(initial = "")

    Surface (color = MaterialTheme.colorScheme.background){
        Box(modifier = Modifier.fillMaxSize()){
            Column (modifier = Modifier.align(Alignment.Center)){
                Text(text = liveDataValue.value ?: " ",
                    fontSize = 26.sp
                )
                Button(onClick = {
                    viewModel.changeLiveDataValue()
                }) {
                    Text(text = "Live Data Button")
                }

                Spacer(modifier = Modifier.padding(10.dp))


                Text(text = stateFlowValue.value,
                    fontSize = 26.sp
                )
                Button(onClick = {
                    viewModel.changeStateFlowValue()
                }) {
                    Text(text = "State Flow Button")
                }

                Spacer(modifier = Modifier.padding(10.dp))


                Text(text = sharedFlowValue.value,
                    fontSize = 26.sp
                )
                Button(onClick = {
                    viewModel.changeSharedFlowValue()
                }) {
                    Text(text = "Shared Flow Button")
                }

            }
        }
    }


}