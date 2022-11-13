package com.example.mae_project.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mae_project.R
import com.example.mae_project.baseclasses.BaseActivity
import com.example.mae_project.databinding.ActivityMainBinding
import com.example.mae_project.fragments.MajorFragment
import com.example.mae_project.fragments.SaveFragment
import com.example.mae_project.fragments.ScholarFragment
import com.example.mae_project.fragments.UniversityFragment
import com.example.mae_project.mvvm.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            mainViewModel.setFragment(UniversityFragment())
        }
        binding.bottomNav.apply {
            itemIconTintList = null
            setOnItemSelectedListener {
                selectFragment(it.itemId)
                true
            }
        }
        mainViewModel.fragment.observe(this) {
            loadFragment(it)
        }
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .replace(R.id.fragContainer, fragment).commit()
    }
    private fun selectFragment(itemId: Int) {
        when (itemId) {
            R.id.btnUniversity -> mainViewModel.setFragment(UniversityFragment())
            R.id.btnMajor -> mainViewModel.setFragment(MajorFragment())
            R.id.btnScholorShip -> mainViewModel.setFragment(ScholarFragment())
            R.id.btnSaveFiles -> mainViewModel.setFragment(SaveFragment())
        }
    }
}