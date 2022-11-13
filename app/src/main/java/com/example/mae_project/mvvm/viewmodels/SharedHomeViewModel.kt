package com.example.mae_project.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mae_project.dataclasses.UniversityDataClass
import com.example.mae_project.mvvm.repos.DatabaseRepository
import com.example.mae_project.mvvm.repos.FireBaseRepo
import com.example.mae_project.roomdatabase.DatabaseDao
import com.example.mae_project.sealdclasses.DataStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedHomeViewModel(val fireBaseRepo: FireBaseRepo) :
    ViewModel() {
    private val _uniData: MutableStateFlow<DataStates> = MutableStateFlow(DataStates.Empty)
    val uniData = _uniData.asStateFlow()

    private val _majorData: MutableStateFlow<DataStates> = MutableStateFlow(DataStates.Empty)
    val majorData = _majorData.asStateFlow()

    private val _scholarData: MutableStateFlow<DataStates> = MutableStateFlow(DataStates.Empty)
    val scholarData = _scholarData.asStateFlow()

    private val _selectedItem: MutableLiveData<Any> = MutableLiveData()
    val selectedItem: LiveData<Any> = _selectedItem

    fun loadUniversities() = viewModelScope.launch {
        _uniData.value = DataStates.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                fireBaseRepo.loadUniversities {
                    if (it.isEmpty()) {
                        _uniData.value = DataStates.Empty
                    } else {
                        _uniData.value = DataStates.Success(it)
                    }
                }
            }


        } catch (ex: Exception) {
            _uniData.value = DataStates.Error(ex.message.toString())
        }
    }

    fun loadMajor() = viewModelScope.launch {
        _majorData.value = DataStates.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                fireBaseRepo.loadMajors {
                    if (it.isEmpty()) {
                        _majorData.value = DataStates.Empty
                    } else {
                        _majorData.value = DataStates.Success(it)
                    }
                }
            }


        } catch (ex: Exception) {
            _majorData.value = DataStates.Error(ex.message.toString())
        }
    }

    fun loadScholars() = viewModelScope.launch {
        _scholarData.value = DataStates.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                fireBaseRepo.loadScholars {
                    if (it.isEmpty()) {
                        _scholarData.value = DataStates.Empty
                    } else {
                        _scholarData.value = DataStates.Success(it)
                    }
                }
            }


        } catch (ex: Exception) {
            _scholarData.value = DataStates.Error(ex.message.toString())
        }
    }

    fun selectedItem(it: Any) = viewModelScope.launch {
        _selectedItem.postValue(it)
    }


}