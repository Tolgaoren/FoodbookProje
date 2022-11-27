package com.toren.foodbookapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.toren.foodbookapp.R
import com.toren.foodbookapp.adapter.viewpager.SearchAdapter
import com.toren.foodbookapp.databinding.SearchFragmentBinding
import com.toren.foodbookapp.model.Yemek
import com.toren.foodbookapp.ui.viewmodel.SearchViewModel

class SearchFragment : Fragment(), SearchView.OnQueryTextListener,
    SearchAdapter.OnItemClickListener {

    private var _binding : SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var recipeList: ArrayList<Yemek>
    private val viewModel: SearchViewModel by viewModels()
    private val args : SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = SearchFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRecipes(args.search)
        loadRecipes()

        binding.addToolbar.toolbar.inflateMenu(R.menu.toolbar_search_item)
        val item = binding.addToolbar.toolbar.menu.findItem(R.id.search_action)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        recipeList = ArrayList()
        searchAdapter = SearchAdapter(recipeList, this)

        binding.apply {
            searchRecyclerView.layoutManager = LinearLayoutManager(context)
            searchRecyclerView.adapter = searchAdapter
            searchRecyclerView.setHasFixedSize(true)
            searchRecyclerView.itemAnimator
        }

    }

    private fun loadRecipes() {
        viewModel.searchRecipeList.observe(viewLifecycleOwner) {
            it?.let {
                println("view")
                searchAdapter.updateList(it as ArrayList<Yemek>)
                recipeList.clear()
                recipeList.addAll(it)
            }
        }
    }
    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.getRecipes(query)
        loadRecipes()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onItemClick(position: Int) {
        val pos = recipeList[position]
        actionToFood(pos)
    }

    private fun actionToFood(pos : Yemek) {
        val action = SearchFragmentDirections.actionSearchFragmentToFoodFragment(pos)
        findNavController().navigate(action)
    }
}