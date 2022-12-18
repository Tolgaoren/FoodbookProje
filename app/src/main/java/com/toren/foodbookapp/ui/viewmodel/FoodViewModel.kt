package com.toren.foodbookapp.ui.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.toren.foodbookapp.model.Users
import com.toren.foodbookapp.model.Yemek
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import java.io.File

class FoodViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val imageRef = Firebase.storage.reference
    val image = MutableLiveData<Bitmap>()
    var recipe = MutableLiveData<Yemek>()
    var name = MutableLiveData<Users>()

    fun thisUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = auth.currentUser!!.uid
            db.collection("users")
                .document(user)
                .get()
                .addOnSuccessListener {
                    it?.let {
                        name.value = it.toObject(Users::class.java)
                    }
                }
        }
    }

    fun getBitmap(imgUrl: String) {
        val localFile = File.createTempFile("tempImage", "jpg")
        imageRef.child(imgUrl).getFile(localFile).addOnSuccessListener {
            image.value = BitmapFactory.decodeFile(localFile.absolutePath)
        }
    }

    fun loadRecipe(arg : Yemek) {
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("foods")
                .document(arg.yemekIsmi + "?user=" + arg.user)
                .get()
                .addOnSuccessListener {
                    it?.let {
                        recipe.value = it.toObject(Yemek::class.java)
                    }
                }
        }
    }

    fun like(food : Yemek) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = food.yemekIsmi + "?user=" + food.user
            val currentUser = auth.currentUser!!.uid
            db.collection("foods")
                .document(id)
                .update("likes", FieldValue.arrayUnion(currentUser))

            db.collection("users")
                .document(currentUser)
                .update("likes",FieldValue.arrayUnion(id))
        }
    }

    fun comment(comment : String, id : String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("foods")
                .document(id)
                .update("comments", FieldValue.arrayUnion(comment))
        }
    }

    fun unlike(food : Yemek) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = food.yemekIsmi + "?user=" + food.user
            val currentUser = auth.currentUser!!.uid
            db.collection("foods")
                .document(id)
                .update("likes", FieldValue.arrayRemove(currentUser))
        }
    }

}