package com.toren.foodbookapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.toren.foodbookapp.model.Users
import com.toren.foodbookapp.model.Yemek
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    val foodList = MutableLiveData<List<Yemek>>()

    fun getFoodData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = db.collection("foods").whereEqualTo("user",auth.uid.toString())
            data.get().addOnSuccessListener {
                if (it != null) {
                    foodList.value = it.toObjects(Yemek::class.java)
                }
            }.addOnFailureListener {

            }
        }
    }

        fun signOut() {
            viewModelScope.launch(Dispatchers.IO) {
                auth.signOut()
            }
        }

    }



















