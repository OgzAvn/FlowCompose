package com.oguz.composeflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    /**
     * flow -> Int -> Flow içerisinde toplanacak ve yayılacak bir tip olacak
     * { } burada yapılacak işleri yayılacak işleri yazıyoruz
     * Coroutine içerisinde oldugu ıcın zaten asynckorn calısır
     */

    val countDownTimerFlow = flow<Int> {

        val countDownFrom = 10
        var counter = countDownFrom
        emit(countDownFrom)

        while (counter > 0){
            delay(1000)
            counter--
            emit(counter)
        }
    }

    init {
        collectInViewModel()
    }

    //TODO: countDownTimerFlow u ViewModel içerisinde de bunu collect etmeyi isteyebilirdik.

    private fun collectInViewModel() {

        viewModelScope.launch {

           countDownTimerFlow
            /*
               .filter {
                   it % 3 == 0
               }
               .map {
                   it + it
               }

             */
               .collect {
                   println("counter is : ${it}")
               }

            /*
                         countDownTimerFlow.onEach {
                             println(it)
                         }.launchIn(viewModelScope)


            //TODO : collectLatest -> en sonuncuyu goster demek değil aslında
            // 2 sn de bir yapıldıgı ıcın collectlatest deger gelmeden yenı deger geliyor
            // bunu collect etmeden siliyor ve en son ıslemde ancak collect ediyor ve onu goruyoruz.
            // delay ı kaldır hiç bir fark olmaz bir sıkıntı olursa ne olacağını
            // anlamında kullanılır.
            countDownTimerFlow.collectLatest {
                //delay(2000)
                println("counter is : ${it}")
            }
            */

        }
    }

    //TODO: LiveData comparisons

    private val _liveData = MutableLiveData<String>("KotlinLiveData") //TODO:Deger vermek zorunda değilsin
    val liveData : LiveData<String> = _liveData //TODO: liveData dışarıdan değiştirilemez

    fun changeLiveDataValue(){
        _liveData.value = "Live Data"
    }

    private val _stateFlow = MutableStateFlow("KotlinStateFlow") //TODO:State lerde deger vermek zorundayız
    val stateFlow = _stateFlow.asStateFlow() //TODO : asStateFlow

    //SharedFlow is highly configurable version of stateFlow.
    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()


    fun changeStateFlowValue() {
        _stateFlow.value = "State Flow"
    }

    fun changeSharedFlowValue() {
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }
}