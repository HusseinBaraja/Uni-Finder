package com.example.mae_project.mvvm.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _fragment: MutableLiveData<Fragment> = MutableLiveData()
    val fragment:LiveData<Fragment> = _fragment

    fun setFragment(value: Fragment) = viewModelScope.launch {
        _fragment.postValue(value)
    }
}