package com.krasovitova.currencywallet.currency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(

) : ViewModel() {
    val currencies = MutableLiveData<List<CurrencyUi>>()

    init {
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {


            //    currencies.postValue(currenciesUi)
        }
    }
}