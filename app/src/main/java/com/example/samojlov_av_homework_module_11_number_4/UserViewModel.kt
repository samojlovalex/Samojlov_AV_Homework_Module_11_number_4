package com.example.samojlov_av_homework_module_11_number_4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class UserViewModel: ViewModel() {
    var listUser: MutableList<String> = mutableListOf()
    val currentUserList: MutableLiveData<MutableList<String>> by lazy { MutableLiveData<MutableList<String>>() }
}