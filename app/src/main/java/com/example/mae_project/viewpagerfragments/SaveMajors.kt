package com.example.mae_project.viewpagerfragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.mae_project.R
import com.example.mae_project.adaptors.MajorAdaptor
import com.example.mae_project.baseclasses.BaseFragment
import com.example.mae_project.bottomsheets.InfoBottomFragment
import com.example.mae_project.databinding.FragmentUniversityBinding
import com.example.mae_project.dataclasses.MajorDataClass
import com.example.mae_project.mvvm.viewmodels.DataBaseViewModel
import com.example.mae_project.sealdclasses.DataStates
import com.example.mae_project.utils.beGone
import com.example.mae_project.utils.beVisible
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SaveMajors : BaseFragment(R.layout.fragment_university) {
    private var scholarAdaptor: MajorAdaptor? = null
    private val dataBaseViewModel: DataBaseViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUniversityBinding.bind(view)
        binding.tvHeader.text = "Saved Majors"
        binding.btnLogOut.beGone()
        dataBaseViewModel.loadMajor()
        initRecycler(adaptorInit = {
            scholarAdaptor = MajorAdaptor(requireContext())
            scholarAdaptor?.onItemCLick = {
                sharedHomeViewModel.selectedItem(it)
                InfoBottomFragment().show(childFragmentManager.beginTransaction(), "Save Majors")
                Unit
            }
        }, recycler = {
            it.adapter = scholarAdaptor
        })
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            dataBaseViewModel.majorData.collectLatest {
                when (it) {
                    is DataStates.Empty -> {
                        with(binding) {
                            mEmpty.beVisible()
                            mProgress.beGone()
                            mRecycler.beGone()
                        }
                    }
                    is DataStates.Loading -> {
                        with(binding) {
                            mEmpty.beGone()
                            mProgress.beVisible()
                            mRecycler.beGone()
                        }
                    }
                    is DataStates.Error -> {
                        with(binding) {
                            mEmpty.beVisible()
                            mProgress.beGone()
                            mRecycler.beGone()
                        }
                    }
                    is DataStates.Success<*> -> {
                        with(binding) {
                            mEmpty.beGone()
                            mProgress.beGone()
                            mRecycler.beVisible()
                            scholarAdaptor?.majorList = it.data as MutableList<MajorDataClass>
                            scholarAdaptor?.mList?.submitList(scholarAdaptor?.majorList)
                        }
                    }
                }
            }
        }
        binding.txtSearch.editText?.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                scholarAdaptor?.mList?.submitList(scholarAdaptor?.majorList)
            } else {
                scholarAdaptor?.mList?.submitList(scholarAdaptor?.majorList?.filter {
                    it.name?.lowercase()?.contains(text.toString()) == true
                })
            }
        }

    }
}