package com.example.androidvk.ui.screens.productlist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidvk.util.Constants.PAGE_SIZE
import com.example.androidvk.entities.ProductEntity
import com.example.androidvk.remote.AndroidVKRepository
import com.example.androidvk.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: AndroidVKRepository
) : ViewModel() {
    private var curPage = 0

    var categories = mutableStateOf<List<String>>(listOf())
    var productList = mutableStateOf<List<ProductEntity>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedProductList = listOf<ProductEntity>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)
    var isCategorySelected = mutableStateOf(false)

    var selectedChip = mutableStateOf("")

    init {
        loadCategories()
        loadProductsPaginated()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            when(val result = repository.getCategories()) {
                is Resource.Success -> {
                    categories.value = result.data!!
                    loadError.value = ""
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun loadCategory(category: String) {
        viewModelScope.launch {
            if (!isCategorySelected.value && !isSearching.value) {
                cachedProductList = productList.value
            }
            isSearching.value = false
            isSearchStarting = true
            isCategorySelected.value = true
            when(val result = repository.getCategory(category)) {
                is Resource.Success -> {
                    val products = result.data!!.products
                    loadError.value = ""
                    productList.value = products
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                }
            }
        }
    }

    fun cancelCategory() {
        productList.value = cachedProductList
        isCategorySelected.value = false
    }

    fun loadProductsPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            when(val result = repository.getProductList(curPage * PAGE_SIZE, PAGE_SIZE)) {
                is Resource.Success -> {
                    //endReached.value = curPage * PAGE_SIZE >= result.data!!.products!!.get(result.data.products.lastIndex)!!.id
                    endReached.value = result.data!!.products.isEmpty()
                    Log.d("debug", "endReached ${endReached.value}")
                    if (endReached.value) return@launch
                    if(result.data.products.isNotEmpty()) {
                        Log.d("debug", "${result.data}")
                        Log.d("debug", "lastID ${result.data.products[result.data.products.lastIndex].id}")
                        Log.d("debug", "curPage $curPage")
                    }

                    val products = result.data.products

                    curPage++
                    loadError.value = ""
                    isLoading.value = false
                    productList.value += products
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun searchProducts(searchText: String) {
        Log.d("debug", "searchText $searchText")
        isSearching.value = true
        if (isCategorySelected.value) {
            selectedChip.value = ""
            cancelCategory()
        }

        if (searchText == "") {
            isSearching.value = false
            productList.value = cachedProductList
            isSearchStarting = true
        } else {
            if (isSearchStarting) {
                if(!isCategorySelected.value) {
                    cachedProductList = productList.value
                }
                isSearchStarting = false
            }
            viewModelScope.launch {
                when(val result = repository.searchProduct(searchText)) {
                    is Resource.Success -> {
                        if(result.data!!.products.isNotEmpty()) {
                            Log.d("debug", "${result.data}")
                            Log.d("debug", "lastID ${result.data.products[result.data.products.lastIndex].id}")
                            Log.d("debug", "curPage $curPage")
                        }

                        val products = result.data.products

                        loadError.value = ""
                        productList.value = products
                    }
                    is Resource.Error -> {
                        loadError.value = result.message!!
                        isSearching.value = false
                    }
                }
            }
        }
    }
}
