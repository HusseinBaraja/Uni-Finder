package com.example.mae_project.activities

import android.os.Bundle
import com.example.mae_project.baseclasses.BaseActivity
import com.example.mae_project.databinding.ActivitySplashBinding
import com.example.mae_project.utils.startNewActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val mAuth: FirebaseAuth by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)
            if (mAuth.currentUser != null) {
                startNewActivity(MainActivity::class.java, true)
            } else {
                startNewActivity(LoginActivity::class.java, true)
            }
        }
    }
}