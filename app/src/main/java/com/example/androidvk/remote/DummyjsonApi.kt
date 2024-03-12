package com.example.androidvk.remote

import com.example.androidvk.entities.ProductList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyjsonApi {
    @GET("/products")
    suspend fun getProductList(
        @Query("skip") skip:Int,
        @Query("limit") limit:Int
    ): ProductList

    @GET("/products/search")
    suspend fun getProductsSearch(
        @Query("q") search:String
    ): ProductList

    @GET("/products/categories")
    suspend fun getCategories(
    ): MutableList<String>

    @GET("/products/category/{category}")
    suspend fun getCategory(
        @Path("category") category:String
    ): ProductList
}
