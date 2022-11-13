package com.example.mae_project.activities

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.mae_project.baseclasses.BaseActivity
import com.example.mae_project.databinding.ActivitySignupBinding
import com.example.mae_project.dataclasses.UserDataClass
import com.example.mae_project.mvvm.viewmodels.SignUpViewModel
import com.example.mae_project.sealdclasses.DialogStates
import com.example.mae_project.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signUpViewModel: SignUpViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            sumbitButton.setOnClickListener {
                val name = namecontainer.editText!!.text.toString()
                val email = emailcontainer.editText!!.text.toString()
                val password = passwordContainer.editText!!.text.toString()
                val phone = phoneContainer.editText!!.text.toString()
                if (name.isEmpty()) {
                    namecontainer.error = "Required..."
                } else if (email.isEmpty()) {
                    emailcontainer.error = "Required..."
                } else if (password.isEmpty()) {
                    passwordContainer.error = "Required..."
                } else if (phone.isEmpty()) {
                    phoneContainer.error = "Required..."
                } else {
                    signUpViewModel.startCreatingAccount(UserDataClass().apply {
                        this.name = name
                        this.email = email
                        this.phoneNo = phone
                    }, password = password, onSuccess = {
                        finish()
                        showToast("Account create successful")
                    }, onFail = {
                        showToast(it)
                    })
                }
            }
        }
        lifeCycleObserver()
    }

    private fun lifeCycleObserver() {
        lifecycleScope.launchWhenStarted {
            signUpViewModel.dialog.collect {
                when (it) {
                    is DialogStates.OnStart -> {
                        mDialog?.showProgressDialog("Creating account...", false)
                    }
                    is DialogStates.OnDismiss -> {
                        mDialog?.dismissDialog()
                    }
                    is DialogStates.OnMessageUpdate -> {
                    }
                }
            }
        }
    }
}