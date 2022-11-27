package com.toren.foodbookapp.adapter.viewpager

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.toren.foodbookapp.databinding.SearchItemBinding
import com.toren.foodbookapp.model.Yemek
import java.io.File

class SearchAdapter (
    private val recipeList : ArrayList<Yemek>,
    private val clickListener: OnItemClickListener
    ) : RecyclerView.Adapter<SearchAdapter.RecipeHolder>() {
    private val imageRef = Firebase.storage.reference

    inner class RecipeHolder(val binding: SearchItemBinding):
            RecyclerView.ViewHolder(binding.root),
        View.OnClickListener{
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position!= RecyclerView.NO_POSITION) {
                clickListener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context),parent ,false)
    return RecipeHolder(binding)}

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        holder.binding.foodName.text = recipeList[position].yemekIsmi
        val localFile = File.createTempFile("tempImage","jpeg")
        imageRef.child(recipeList[position].imgUrl).getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            holder.binding.foodImage.load(bitmap)
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newRecipeList : ArrayList<Yemek>) {
        recipeList.clear()
        println("adapter")
        recipeList.addAll(newRecipeList)
        notifyDataSetChanged()
    }
}