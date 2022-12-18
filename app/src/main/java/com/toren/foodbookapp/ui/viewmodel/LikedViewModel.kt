package com.toren.foodbookapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.toren.foodbookapp.model.Users
import com.toren.foodbookapp.model.Yemek
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class LikedViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    var likedList = MutableLiveData<List<Yemek>>()
    val thisUser = MutableLiveData<List<Users>>()


    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = auth.currentUser!!.uid
            db.collection("users").whereEqualTo("uuid",currentUser)
                .get().addOnSuccessListener {
                    if (it != null) {
                        thisUser.value = it.toObjects(Users::class.java)
                    }
                }
        }
    }

    fun getLiked(list : ArrayList<String>) {
        viewModelScope.launch {
            for (i in list) {
            val data = db.collection("foods")
                .whereEqualTo("id", i)

            data.get().addOnSuccessListener {
                if (it != null) {
                    println("başarılı")
                    likedList.value = it.toObjects(Yemek::class.java)
                } else {
                    println("Empty list.")
                }
            }
        }
    }
    }


}