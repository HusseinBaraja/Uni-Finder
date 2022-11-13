package com.example.mae_project.sealdclasses

sealed class DialogStates {
    object OnStart : DialogStates()
    object OnDismiss : DialogStates()
    class OnMessageUpdate(val message: String) : DialogStates()
}
