package com.krasovitova.currencywallet.avatar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krasovitova.currencywallet.data.AvatarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SelectAvatarViewModel @Inject constructor(
    private val avatarRepository: AvatarRepository
) : ViewModel() {

    val images = MutableLiveData<List<ImageUi>>()

    init {
        getImages()
    }

    private fun getImages() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = avatarRepository.getImages()
            images.postValue(result)
        }
    }
}