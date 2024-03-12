package com.example.androidvk.ui.screens

import android.util.Log
import com.example.androidvk.entities.ProductEntity

object CurrentProduct {
    var product: ProductEntity = ProductEntity(-1, "", "", 0, 0f, 0f, 0, "", "", "", listOf())
        private set

    fun setCurrent(product: ProductEntity) {
        this.product = product
        Log.d("current", "${this.product}")
    }
}