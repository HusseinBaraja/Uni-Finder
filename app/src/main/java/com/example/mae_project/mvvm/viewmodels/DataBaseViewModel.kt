package com.example.mae_project.mvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mae_project.dataclasses.MajorDataClass
import com.example.mae_project.dataclasses.ScholarDataClass
import com.example.mae_project.dataclasses.UniversityDataClass
import com.example.mae_project.mvvm.repos.DatabaseRepository
import com.example.mae_project.sealdclasses.DataStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DataBaseViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {

    private val _uniData: MutableStateFlow<DataStates> = MutableStateFlow(DataStates.Empty)
    val uniData = _uniData.asStateFlow()

    private val _majorData: MutableStateFlow<DataStates> = MutableStateFlow(DataStates.Empty)
    val majorData = _majorData.asStateFlow()

    private val _scholarData: MutableStateFlow<DataStates> = MutableStateFlow(DataStates.Empty)
    val scholarData = _scholarData.asStateFlow()

    fun loadUniversities() = viewModelScope.launch {
        _uniData.value = DataStates.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val list = databaseRepository.universities?.getData()
                if (list?.isEmpty() == true) {
                    _uniData.value = DataStates.Empty
                } else {
                    _uniData.value = DataStates.Success(list)
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
                val list = databaseRepository.majors?.getData()
                if (list?.isEmpty() == true) {
                    _majorData.value = DataStates.Empty
                } else {
                    _majorData.value = DataStates.Success(list)
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
                val list = databaseRepository.scholarShip?.getData()
                if (list?.isEmpty() == true) {
                    _scholarData.value = DataStates.Empty
                } else {
                    _scholarData.value = DataStates.Success(list)
                }
            }


        } catch (ex: Exception) {
            _scholarData.value = DataStates.Error(ex.message.toString())
        }
    }

    fun insertData(value: Any?) = viewModelScope.launch(Dispatchers.IO) {
        when (value) {
            is UniversityDataClass -> {
                databaseRepository.universities?.insertData(value)
            }
            is MajorDataClass -> {
                databaseRepository.majors?.insertData(value)

            }
            is ScholarDataClass -> {
                databaseRepository.scholarShip?.insertData(value)
            }
        }
    }

    fun deleteData(value: Any?) = viewModelScope.launch(Dispatchers.IO) {
        when (value) {
            is UniversityDataClass -> {
                databaseRepository.universities?.deleteData(value.id!!)
                loadUniversities()
            }
            is MajorDataClass -> {
                databaseRepository.majors?.deleteData(value.id!!)
                loadMajor()
            }
            is ScholarDataClass -> {
                databaseRepository.scholarShip?.deleteData(value.id!!)
                loadScholars()
            }
        }
    }
}