package com.example.mae_project.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.mae_project.R
import com.example.mae_project.adaptors.ScholarShipAdaptor
import com.example.mae_project.baseclasses.BaseFragment
import com.example.mae_project.bottomsheets.InfoBottomFragment
import com.example.mae_project.databinding.FragmentUniversityBinding
import com.example.mae_project.dataclasses.ScholarDataClass
import com.example.mae_project.sealdclasses.DataStates
import com.example.mae_project.utils.beGone
import com.example.mae_project.utils.beVisible
import kotlinx.coroutines.flow.collectLatest


class ScholarFragment : BaseFragment(R.layout.fragment_university) {
    private var scholarAdaptor: ScholarShipAdaptor? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUniversityBinding.bind(view)
        binding.tvHeader.text = "ScholarShips"
        sharedHomeViewModel.loadScholars()
        initRecycler(adaptorInit = {
            scholarAdaptor = ScholarShipAdaptor(requireContext())
            scholarAdaptor?.onItemCLick = {
                sharedHomeViewModel.selectedItem(it)
                InfoBottomFragment().show(childFragmentManager.beginTransaction(), "Scholar")
                Unit
            }
        }, recycler = {
            it.adapter = scholarAdaptor
        })
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            sharedHomeViewModel.scholarData.collectLatest {
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
                            scholarAdaptor?.scholarList = it.data as MutableList<ScholarDataClass>
                            scholarAdaptor?.mList?.submitList(scholarAdaptor?.scholarList)
                        }
                    }
                }
            }
        }
        binding.txtSearch.editText?.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                scholarAdaptor?.mList?.submitList(scholarAdaptor?.scholarList)
            } else {
                scholarAdaptor?.mList?.submitList(scholarAdaptor?.scholarList?.filter {
                    it.title?.lowercase()?.contains(text.toString()) == true
                })
            }
        }

    }
}