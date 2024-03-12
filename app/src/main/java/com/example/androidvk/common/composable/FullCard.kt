package com.example.androidvk.common.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.androidvk.entities.ProductEntity
import com.example.androidvk.ui.theme.Crimson
import com.example.androidvk.ui.theme.PaleGreen
import com.example.androidvk.ui.theme.Rating

@Composable
fun CardDetail(
    product: ProductEntity
) {
    Column(Modifier.fillMaxSize()) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            product.images?.let { ImageSlider(it) }
        }
        Row (modifier = Modifier
            .padding(vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFDCDCDC)),

            ) {
                Text(
                    text = " ★ " + product.rating + " ",
                    color = Rating,
                    fontSize = 20.sp
                )
            }
            Spacer(Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Crimson),

                ) {
                Text(
                    text = " - " + product.discountPercentage + "% ",
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }
            Spacer(Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFDCDCDC)),

                ) {
                Text(
                    text = " " + product.category + " ",
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFDCDCDC)),

            ) {
            Column {
                Row (modifier = Modifier.padding(8.dp)) {
                    Icon(

                        imageVector = Icons.Filled.Check,
                        contentDescription = "check",
                        tint = Color.Green
                    )
                    Text(
                        text = " " + product.brand + " ",
                        color = Color.Black,
                        fontSize = 20.sp,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.weight(1f))
                }
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = " " + product.description + " ",
                    color = Color.Black,
                    fontSize = 20.sp
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .padding(8.dp)

                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(PaleGreen)
                            .padding(8.dp)

                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = " " + product.price + "$ ",
                            color = Color.Black,
                            fontSize = 24.sp,
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .padding(8.dp)

                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = " " + product.stock + " in stock ",
                            color = Color.Black,
                            fontSize = 20.sp,
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(images: List<String>) {
    val pagerState = rememberPagerState(pageCount = {
        images.size
    })
    HorizontalPager(state = pagerState) { page ->
        DrawImageDetail(images[page], Modifier)
    }
}

@Composable
fun DrawImageDetail(
    content: String,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Card(
            modifier = modifier.fillMaxSize()

        ) {
            Image(
                painter = rememberAsyncImagePainter(content),
                contentDescription = "image",
                modifier = modifier
                    .fillMaxSize()
                    .clip(shape = RectangleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}