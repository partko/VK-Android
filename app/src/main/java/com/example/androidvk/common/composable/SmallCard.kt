package com.example.androidvk.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.androidvk.MainActivity
import com.example.androidvk.entities.ProductEntity
import com.example.androidvk.ui.theme.Rating
import java.math.RoundingMode
import java.time.format.TextStyle

@Composable
fun SmallCard(
    navController: NavController? = null,
    modifier: Modifier = Modifier,
    product: ProductEntity,
    onEditClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            //.shadow(5.dp, RoundedCornerShape(10.dp))
            //.clip(RoundedCornerShape(10.dp))
            .height(240.dp)
            .clickable {
                onEditClick()
                navController!!.navigate("product_info_screen")
            }
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(product.images!![0]),
                contentDescription = "image",
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .align(CenterHorizontally)
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                Text(
                    text = product.price.toString() + "$ ",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    maxLines = 1
                )
                Text(
                    text = "" + (product.price / (100 - product.discountPercentage!!) * 100).toBigDecimal().setScale(0, RoundingMode.UP),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    maxLines = 1,
                    //style = androidx.compose.ui.text.TextStyle(textDecoration = TextDecoration.LineThrough)
                    style = LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                )
                Text(
                    text = " -" + product.discountPercentage.toBigDecimal().setScale(0, RoundingMode.UP) + "%",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    maxLines = 1
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "★ " + product.rating,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Rating,
                    maxLines = 1,
                )
            }
            Text(
                text = product.title,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            Text(
                text = product.description,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

