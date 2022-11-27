package com.toren.foodbookapp.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.toren.foodbookapp.R
import com.toren.foodbookapp.adapter.IngredientsAdapter
import com.toren.foodbookapp.databinding.FoodFragmentBinding
import com.toren.foodbookapp.model.Yemek
import com.toren.foodbookapp.ui.viewmodel.FoodViewModel

class FoodFragment : Fragment() {

    private val viewModel: FoodViewModel by viewModels()
    private var _binding: FoodFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var materialAdapter : IngredientsAdapter
    private val args: FoodFragmentArgs by navArgs()
    private val auth = Firebase.auth
    private lateinit var recipe : Yemek

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FoodFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadRecipe(args.gelenYemek)
        loadRecipe()

        binding.apply {


            likeButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    likeButton.background = context?.getDrawable(R.drawable.ic_favorite)
                    viewModel.like(recipe)

                }
                else if (!isChecked) {
                    likeButton.background = context?.getDrawable(R.drawable.ic_favorite_border)
                    viewModel.unlike(recipe)
                }
            }
        }

    }

    private fun loadRecipe() {
        viewModel.recipe.observe(viewLifecycleOwner){
            it?.let {
                recipe = it
                loadAll()
            }
        }
    }
    private fun loadAll() {
        binding.apply {
            foodName.text = recipe.yemekIsmi
            println(recipe)
            foodUser.text = recipe.user
            foodAciklama.text = recipe.aciklama
            foodHazirlanis.text = recipe.hazirlanis
            viewModel.getBitmap(recipe.imgUrl)
            viewModel.image.observe(viewLifecycleOwner) {
                it?.let {
                    foodImage.load(it)
                }
            }
            val likeCheck = recipe.likes.filter { it.contains(auth.currentUser!!.uid)}

            if(likeCheck.isNotEmpty()) {
                likeButton.background = context?.getDrawable(R.drawable.ic_favorite)
            }
            else if(likeCheck.isEmpty()){
                likeButton.background = context?.getDrawable(R.drawable.ic_favorite_border)
            }
            materialAdapter = IngredientsAdapter(recipe.malzemeler)


            recyclerViewMalzemeler.layoutManager = LinearLayoutManager(view?.context)
            recyclerViewMalzemeler.adapter = materialAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}