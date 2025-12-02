package com.example.quotesbrowserapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quotesbrowserapp.services.ApiService
import com.example.quotesbrowserapp.model.PicsumItem
import com.example.quotesbrowserapp.repository.QuoteRepository
import com.example.quotesbrowserapp.services.ApiClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuoteViewModel(application: Application) : AndroidViewModel(application) {

    private val _picsList = MutableLiveData<List<PicsumItem>>()
    val picsList: LiveData<List<PicsumItem>> get() = _picsList

    private val _favorites = MutableLiveData<List<PicsumItem>>(emptyList())
    val favorites: LiveData<List<PicsumItem>> get() = _favorites

    private val gson = Gson()

    private val repository =  QuoteRepository(ApiClient.apiService)

    init {
        fetchPics()
    }

    private fun fetchPics() {
        viewModelScope.launch {
            try {
                val list = repository.getPicsList()
                _picsList.value = list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Load favorites from SharedPreferences
    fun loadFavorites(context: Context) {
        val prefs = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
        val json = prefs.getString("favorites_list", null)
        _favorites.value = if (json.isNullOrEmpty()) emptyList()
        else gson.fromJson(json, object : TypeToken<List<PicsumItem>>() {}.type)
    }

    // Add item to favorites and save to SharedPreferences
    fun addFavorite(item: PicsumItem, context: Context) {
        val current = _favorites.value?.toMutableList() ?: mutableListOf()
        current.add(item)
        _favorites.value = current

        val prefs = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("favorites_list", gson.toJson(current)).apply()
    }
    fun removeFavorite(item: PicsumItem) {
        val current = _favorites.value.orEmpty().toMutableList()
        current.remove(item)
        _favorites.value = current
    }
}