package com.example.mae_project.sealdclasses

sealed class DataStates {
    object Loading : DataStates()
    object Empty : DataStates()
    class Success<T>(val data: T) : DataStates()
    class Error(message: String) : DataStates()
}