package com.example.mae_project.fragments

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
import com.example.mae_project.sealdclasses.DataStates
import com.example.mae_project.utils.beGone
import com.example.mae_project.utils.beVisible
import kotlinx.coroutines.flow.collectLatest


class MajorFragment : BaseFragment(R.layout.fragment_university) {
    private var majorAdaptor: MajorAdaptor? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUniversityBinding.bind(view)
        binding.tvHeader.text = "Majors"
        initRecycler(adaptorInit = {
            majorAdaptor = MajorAdaptor(requireContext())
            majorAdaptor?.onItemCLick = {
                sharedHomeViewModel.selectedItem(it)
                InfoBottomFragment().show(childFragmentManager.beginTransaction(), "Majors")
                Unit
            }
        }, recycler = {
            it.adapter = majorAdaptor
        })
        sharedHomeViewModel.loadMajor()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            sharedHomeViewModel.majorData.collectLatest {
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
                            majorAdaptor?.majorList = it.data as MutableList<MajorDataClass>
                            majorAdaptor?.mList?.submitList(majorAdaptor?.majorList)
                        }
                    }
                }
            }
        }
        binding.txtSearch.editText?.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                majorAdaptor?.mList?.submitList(majorAdaptor?.majorList)
            } else {
                majorAdaptor?.mList?.submitList(majorAdaptor?.majorList?.filter {
                    it.name?.lowercase()?.contains(text.toString()) == true
                })
            }
        }
    }
}