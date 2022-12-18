package com.toren.foodbookapp.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.toren.foodbookapp.R
import com.toren.foodbookapp.adapter.FoodItemAdapter
import com.toren.foodbookapp.databinding.AccountFragmentBinding
import com.toren.foodbookapp.databinding.LikedFragmentBinding
import com.toren.foodbookapp.model.Users
import com.toren.foodbookapp.model.Yemek
import com.toren.foodbookapp.ui.viewmodel.AccountViewModel
import com.toren.foodbookapp.ui.viewmodel.LikedViewModel


class LikedFragment : Fragment(),
    FoodItemAdapter.OnItemClickListener {

    private val viewModel: LikedViewModel by viewModels()
    private var _binding: LikedFragmentBinding? = null
    private val binding get() = _binding!!
    private var foodAdapter = FoodItemAdapter(arrayListOf(), this)
    private var likeList = ArrayList<Yemek>(arrayListOf())
    private lateinit var thisUser: Users

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = LikedFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser()
        loadThisUser()

        binding.apply {

            recylerViewLikes.layoutManager = GridLayoutManager(view.context, 2)
            recylerViewLikes.adapter = foodAdapter
            recylerViewLikes.setHasFixedSize(true)

        }
    }

    private fun loadThisUser() {
        viewModel.thisUser.observe(viewLifecycleOwner) {
            it?.let {
                thisUser = it[0]
                viewModel.getLiked(thisUser.likes)
                likedFoodData()
            }
        }
    }

    override fun onItemClick(position: Int) {
        val food = likeList[position]
        val action = AccountFragmentDirections.actionAccountFragmentToFoodFragment(food)
        findNavController().navigate(action)
    }


    private fun likedFoodData() {
        viewModel.likedList.observe(viewLifecycleOwner) {
            it?.let {
                println(it)
                foodAdapter.updateLikes(it as ArrayList<Yemek>)
                likeList.addAll(it)
                println(likeList)
            }
        }
    }

}