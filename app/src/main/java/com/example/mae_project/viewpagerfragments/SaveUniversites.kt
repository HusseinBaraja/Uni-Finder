package com.example.mae_project.viewpagerfragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.mae_project.R
import com.example.mae_project.adaptors.UniAdaptor
import com.example.mae_project.baseclasses.BaseFragment
import com.example.mae_project.bottomsheets.InfoBottomFragment

import com.example.mae_project.databinding.FragmentUniversityBinding
import com.example.mae_project.dataclasses.UniversityDataClass
import com.example.mae_project.mvvm.viewmodels.DataBaseViewModel
import com.example.mae_project.sealdclasses.DataStates
import com.example.mae_project.utils.beGone
import com.example.mae_project.utils.beVisible
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SaveUniversites : BaseFragment(R.layout.fragment_university) {
    private val dataBaseViewModel: DataBaseViewModel by sharedViewModel()
    private var uniAdaptor: UniAdaptor? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUniversityBinding.bind(view)
        binding.tvHeader.text = "Saved Universities"
        binding.btnLogOut.beGone()

        initRecycler(adaptorInit = {
            uniAdaptor = UniAdaptor(requireContext())
            uniAdaptor?.onItemCLick = {
                sharedHomeViewModel.selectedItem(it)
                InfoBottomFragment().show(childFragmentManager.beginTransaction(), "Save University")
                Unit
            }
        }, recycler = {
            it.adapter = uniAdaptor
        })
        dataBaseViewModel.loadUniversities()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            dataBaseViewModel.uniData.collectLatest {
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
                            uniAdaptor?.uniList = it.data as MutableList<UniversityDataClass>
                            uniAdaptor?.mList?.submitList(uniAdaptor?.uniList)
                        }
                    }
                }
            }
        }
        binding.txtSearch.editText?.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                uniAdaptor?.mList?.submitList(uniAdaptor?.uniList)
            } else {
                uniAdaptor?.mList?.submitList(uniAdaptor?.uniList?.filter {
                    it.uniName?.lowercase()?.contains(text.toString()) == true || it.fee
                        ?.contains(text.toString().lowercase()) == true
                })
            }
        }

    }
}