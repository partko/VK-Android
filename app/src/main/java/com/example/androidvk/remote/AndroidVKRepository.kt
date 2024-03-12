package com.example.androidvk.remote

import com.example.androidvk.entities.ProductList
import com.example.androidvk.util.Resource
import javax.inject.Inject

class AndroidVKRepository @Inject constructor(
    private val api: DummyjsonApi
) {
    suspend fun getProductList(limit: Int, offset: Int): Resource<ProductList> {
        val response = try {
            api.getProductList(limit, offset)
        } catch(e: Exception) {
            return Resource.Error("An unknown error :(")
        }
        return Resource.Success(response)
    }

    suspend fun searchProduct(search: String): Resource<ProductList> {
        val response = try {
            api.getProductsSearch(search)
        } catch(e: Exception) {
            return Resource.Error("An unknown error :(")
        }
        return Resource.Success(response)
    }

    suspend fun getCategories(): Resource<MutableList<String>> {
        val response = try {
            api.getCategories()
        } catch(e: Exception) {
            return Resource.Error("An unknown error :(")
        }
        return Resource.Success(response)
    }

    suspend fun getCategory(category: String): Resource<ProductList> {
        val response = try {
            api.getCategory(category)
        } catch(e: Exception) {
            return Resource.Error("An unknown error :(")
        }
        return Resource.Success(response)
    }
}