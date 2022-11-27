package com.toren.foodbookapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.toren.foodbookapp.R
import com.toren.foodbookapp.adapter.viewpager.HomeViewPagerAdapter
import com.toren.foodbookapp.ui.viewmodel.HomeViewModel
import com.toren.foodbookapp.databinding.HomeFragmentBinding
import com.toren.foodbookapp.utils.Constants

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(layoutInflater)

        val adapter = HomeViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        binding.toolbarm.toolbar.inflateMenu(R.menu.toolbar_search_item)
        val item = binding.toolbarm.toolbar.menu.findItem(R.id.search_action)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = Constants.yemekKategorileri[position]
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            robot.setOnClickListener {
                actionToSearchFood()
            }

        }

    }

    override fun onQueryTextSubmit(query: String): Boolean {
        actionToSearch(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun actionToSearchFood() {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFoodFragment()
        findNavController().navigate(action)
    }
    private fun actionToSearch(query : String) {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(query)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}