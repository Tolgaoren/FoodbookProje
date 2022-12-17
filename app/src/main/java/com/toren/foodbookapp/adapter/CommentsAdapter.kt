package com.toren.foodbookapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toren.foodbookapp.databinding.FoodCommentsBinding

class CommentsAdapter(
    private val comments : ArrayList<String>
) : RecyclerView.Adapter<CommentsAdapter.CommentsHolder>() {

    inner class CommentsHolder(val binding : FoodCommentsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsHolder {
        val binding = FoodCommentsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommentsHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsHolder, position: Int) {
        holder.binding.comment.text = comments[position]
    }

    override fun getItemCount(): Int {
        return comments.size
    }
    fun updateList(newMaterialList: ArrayList<String>) {
        comments.clear()
        comments.addAll(newMaterialList)
        notifyDataSetChanged()
    }

}