package com.toren.foodbookapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toren.foodbookapp.databinding.FoodIngredientsBinding

class IngredientsAdapter(
    private val ingredients : ArrayList<String>
) : RecyclerView.Adapter<IngredientsAdapter.IngredientsHolder>() {

    inner class IngredientsHolder(val binding : FoodIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsHolder {
        val binding = FoodIngredientsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return IngredientsHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientsHolder, position: Int) {
        holder.binding.ingredient.text = ingredients[position]
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }
    fun updateList(newMaterialList: ArrayList<String>) {
        ingredients.clear()
        ingredients.addAll(newMaterialList)
        notifyDataSetChanged()
    }

}