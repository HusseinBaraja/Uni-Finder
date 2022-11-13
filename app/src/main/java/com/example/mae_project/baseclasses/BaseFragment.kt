package com.example.mae_project.baseclasses

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mae_project.activities.LoginActivity
import com.example.mae_project.databinding.FragmentUniversityBinding
import com.example.mae_project.mvvm.viewmodels.SharedHomeViewModel
import com.example.mae_project.utils.startNewActivity
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    val sharedHomeViewModel: SharedHomeViewModel by sharedViewModel()
    lateinit var binding: FragmentUniversityBinding
    private val mAuth: FirebaseAuth by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        binding.btnLogOut.setOnClickListener {
            mAuth.signOut()
            context?.startNewActivity(LoginActivity::class.java, true)
        }
    }

    fun initRecycler(adaptorInit: () -> Unit, recycler: (RecyclerView) -> Unit) {
        adaptorInit()
        binding.mRecycler.layoutManager = LinearLayoutManager(context)
        recycler(binding.mRecycler)
    }
}