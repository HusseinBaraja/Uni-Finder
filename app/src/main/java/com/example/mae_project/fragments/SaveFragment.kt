package com.example.mae_project.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mae_project.R
import com.example.mae_project.databinding.FragmentSaveBinding
import com.example.mae_project.viewpagerfragments.SaveMajors
import com.example.mae_project.viewpagerfragments.SaveScholars
import com.example.mae_project.viewpagerfragments.SaveUniversites
import com.google.android.material.tabs.TabLayoutMediator


class SaveFragment : Fragment(R.layout.fragment_save) {
    private lateinit var binding: FragmentSaveBinding
    private val tabIndicators = arrayOf(
        Pair(R.drawable.saveuniversityicon, "Universities"),
        Pair(R.drawable.savemajoricon, "Major"),
        Pair(R.drawable.savescholaricon, "ScholarShip")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSaveBinding.bind(view)
        initTabPager()
    }

    private fun initTabPager() {
        with(binding) {
            viewPager.adapter =
                FragmentAdaptor(
                    listOf(SaveUniversites(), SaveMajors(), SaveScholars()),
                    childFragmentManager,
                    viewLifecycleOwner.lifecycle
                )
            TabLayoutMediator(mTabLayout, viewPager) { tab, position ->
                tab.icon = ContextCompat.getDrawable(requireContext(), tabIndicators[position].first)
               // tab.text = tabIndicators[position].second
            }.attach()
        }
    }

    inner class FragmentAdaptor(
        private val fragments: List<Fragment>,
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }
}