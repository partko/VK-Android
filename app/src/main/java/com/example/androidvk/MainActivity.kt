package com.example.androidvk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidvk.ui.screens.productlinfo.ProductInfoScreen
import com.example.androidvk.ui.screens.productlist.ProductListScreen
import com.example.androidvk.ui.theme.AndroidVKTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidVKTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "product_list_screen"
                ) {
                    composable("product_list_screen") {
                        ProductListScreen(navController = navController)
                    }
                    composable("product_info_screen") {
                        ProductInfoScreen(navController = navController)
                    }
                }
            }
        }
    }
}
