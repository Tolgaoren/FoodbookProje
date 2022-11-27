package com.toren.foodbookapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.toren.foodbookapp.model.Yemek
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val db = Firebase.firestore
    val searchRecipeList = MutableLiveData<List<Yemek>>()
    private val auth = Firebase.auth

    fun getRecipes(search : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = db.collection("foods")
                .whereEqualTo("yemekIsmi", search)

            data.get().addOnSuccessListener {
                if (it != null) {
                    println("viewmodel")
                    searchRecipeList.value = it.toObjects(Yemek::class.java)
                }
            }.addOnFailureListener {
            }
        }
    }
}