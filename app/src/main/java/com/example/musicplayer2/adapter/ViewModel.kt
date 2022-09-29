package com.example.musicplayer2.adapter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {

    val index: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

}