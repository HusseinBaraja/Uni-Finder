package com.example.mae_project.activities

import android.os.Bundle

import com.example.mae_project.baseclasses.BaseActivity
import com.example.mae_project.databinding.ActivityLoginBinding
import com.example.mae_project.utils.showToast
import com.example.mae_project.utils.startNewActivity
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val mAuth:FirebaseAuth by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signIn.setOnClickListener {
            with(binding) {
                if (tvEmail.editText?.text?.isEmpty() == true) {
                    tvEmail.error = "Required..."
                } else if (tvPassword.editText?.text?.isEmpty() == true) {
                    tvPassword.error = "Required..."
                } else {
                    mAuth.signInWithEmailAndPassword(
                        tvEmail.editText?.text?.toString()!!,
                        tvPassword.editText?.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            startNewActivity(MainActivity::class.java, true)
                        } else {
                            showToast("username or password is incorrect")
                        }
                    }.addOnFailureListener {
                        showToast("Fail to login....")
                    }
                }
            }
        }
        binding.signUp.setOnClickListener {
            startNewActivity(SignUpActivity::class.java, false)
        }
    }
}