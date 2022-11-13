package com.example.mae_project.appclass

import android.app.Application
import com.example.mae_project.mvvm.repos.DatabaseRepository
import com.example.mae_project.mvvm.viewmodels.MainViewModel
import com.example.mae_project.mvvm.viewmodels.SharedHomeViewModel
import com.example.mae_project.utils.FireBaseRefrences
import com.example.mae_project.mvvm.repos.FireBaseRepo
import com.example.mae_project.mvvm.viewmodels.DataBaseViewModel
import com.example.mae_project.mvvm.viewmodels.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ApplicationClass)
            modules(
                listOf(
                    appModule
                )
            )
        }
    }

    private val appModule = module {
        single { FireBaseRefrences() }
        single { FirebaseAuth.getInstance() }
        single { FireBaseRepo(get(),androidContext(), get()) }
        single { DatabaseRepository(androidContext()) }
        viewModel { MainViewModel() }
        viewModel { SharedHomeViewModel(get()) }
        viewModel { DataBaseViewModel(get()) }
        viewModel { SignUpViewModel(get()) }
    }
}