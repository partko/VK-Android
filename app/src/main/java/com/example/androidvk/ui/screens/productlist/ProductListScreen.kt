package com.example.androidvk.ui.screens.productlist

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidvk.common.composable.ChipGroupCompose
import com.example.androidvk.common.composable.SearchBar
import com.example.androidvk.common.composable.SmallCard
import com.example.androidvk.entities.ProductEntity
import com.example.androidvk.ui.screens.CurrentProduct

@Composable
fun ProductListScreen(
    navController: NavController,
    viewModel: ProductListViewModel = hiltViewModel()
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            SearchBar(
                hint = "Search",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                viewModel.searchProducts(it)
            }
            //Spacer(modifier = Modifier.height(2.dp))
            ChipsRow(viewModel = viewModel)
            Spacer(modifier = Modifier.height(8.dp))
            ProductList(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun ChipsRow(
    viewModel: ProductListViewModel
) {
    val categories by remember { viewModel.categories }
    val scrollState = rememberScrollState()
    Row (modifier = Modifier.horizontalScroll(scrollState)) {
        ChipGroupCompose(
            chipList = categories,
            viewModel = viewModel,
            selectedChip = viewModel.selectedChip
        )
    }
}

@Composable
fun ProductList(
    navController: NavController,
    viewModel: ProductListViewModel
) {
    val productList by remember { viewModel.productList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }
    val isCategory by remember { viewModel.isCategorySelected }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if(productList.size % 2 == 0) {
            productList.size / 2
        } else {
            productList.size / 2 + 1
        }
        items(itemCount) {
            if(it >= itemCount - 1 && !endReached && !isLoading && !isSearching && !isCategory) {
                viewModel.loadProductsPaginated()
            }
            ProductRow(rowIndex = it, products = productList, navController = navController)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator()
        }
        if(loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadProductsPaginated()
            }
        }
    }

}

@Composable
fun ProductRow(
    navController: NavController? = null,
    rowIndex: Int,
    products: List<ProductEntity>
) {
    Column {
        Row {
            SmallCard(
                navController = navController,
                product = products[rowIndex * 2],
                modifier = Modifier.weight(1f)
            ) {
                val current = CurrentProduct
                current.setCurrent(products[rowIndex * 2])
            }
            Spacer(modifier = Modifier.width(16.dp))
            if (products.size >= rowIndex * 2 + 2) {
                SmallCard(
                    navController = navController,
                    product = products[rowIndex * 2 + 1],
                    modifier = Modifier.weight(1f)
                ) {
                    val current = CurrentProduct
                    current.setCurrent(products[rowIndex * 2 + 1])
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}
