package com.example.mae_project.mvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mae_project.dataclasses.UserDataClass
import com.example.mae_project.mvvm.repos.FireBaseRepo
import com.example.mae_project.sealdclasses.DialogStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(val mRepo: FireBaseRepo) : ViewModel() {
    private val _dialog: MutableStateFlow<DialogStates> = MutableStateFlow(DialogStates.OnDismiss)
    val dialog: StateFlow<DialogStates> = _dialog

    fun startCreatingAccount(
        value: UserDataClass, password:String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        _dialog.value = DialogStates.OnStart
        mRepo.createAccount(value,password, onSuccess = {
            _dialog.value = DialogStates.OnDismiss
            viewModelScope.launch(Dispatchers.Main) {
                onSuccess()
            }
        }, onFail = {
            _dialog.value = DialogStates.OnDismiss
            viewModelScope.launch(Dispatchers.Main) {
                onFail.invoke(it)
            }
        })
    }
/*
    fun loadMyInfo()=viewModelScope.launch(Dispatchers.IO) {
        mRepo.loadMyInfo()
    }*/
}