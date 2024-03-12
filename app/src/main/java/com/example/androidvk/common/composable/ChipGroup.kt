package com.example.androidvk.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidvk.ui.screens.productlist.ProductListViewModel
import com.example.androidvk.ui.theme.Crimson

@Composable
fun ChipGroupCompose(
    chipList: List<String> = listOf(),
    viewModel: ProductListViewModel,
    selectedChip: MutableState<String>
) {
    //var selected by remember { mutableStateOf("") }
    //var selected by remember { mutableStateOf(selectedChip) }
    var selected by remember { selectedChip }
    Row(
        modifier = Modifier
            .padding(start = 32.dp,top = 2.dp)
            .fillMaxWidth()
    ) {
        chipList.forEach { it ->
            Chip(
                title = it,
                selected = selected,
                onSelected = {
                    selected = if (selected == it) {
                        ""
                    } else {
                        it
                    }
                },
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun Chip(
    title: String,
    selected: String,
    onSelected: (String) -> Unit,
    viewModel: ProductListViewModel
) {
    val isSelected = selected == title
    val background = if (isSelected) Crimson else Color.LightGray
    val contentColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .padding(end = 10.dp)
            .height(35.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(
                onClick = {
                    onSelected(title)
                    if (!isSelected) {
                        viewModel.loadCategory(title)
                    } else {
                        viewModel.cancelCategory()
                    }
                }
            )
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = title, color = contentColor, fontSize = 16.sp)
        }
    }
}
