package com.example.androidvk.entities

data class ProductEntity(
    val id :Int,
    val title :String,
    val description :String,
    val price :Int,
    val discountPercentage :Float?=null,
    val rating :Float?=null,
    val stock :Int?=null,
    val brand :String?=null,
    val category :String?=null,
    val thumbnail :String,
    val images :List<String>?=null
)

data class ProductList(
    val products: List<ProductEntity>
)
