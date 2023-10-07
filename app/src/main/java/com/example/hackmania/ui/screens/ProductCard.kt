package com.example.hackmania.ui.screens

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hackmania.ProductDetails
import com.example.hackmania.R
import com.example.hackmania.model.Global
import com.example.hackmania.model.Product
import com.example.hackmania.ui.theme.green
import com.example.hackmania.ui.theme.lightpeach
import com.example.hackmania.ui.theme.peach
import com.example.hackmania.ui.theme.pinkMera

@Composable
fun ProductCard(product: Product){
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFCCCCCC)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Global.product = product
                val intent = Intent(context, ProductDetails::class.java)
                context.startActivity(intent)
            }
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
        ){
            AsyncImage(
                model = product.productImage,
                contentDescription = "",
                error = painterResource(id = R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = product.companyName,
                color = Color.Black,
                fontSize = 20.sp
            )
            Text(
                text = product.productName,
                color = Color.Black,
                fontSize = 16.sp
            )
            Text(
                text = product.price+" /-",
                color = Color.Black,
                fontSize = 18.sp
            )
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, peach, RoundedCornerShape(10.dp))
                    .background(Color.White),
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(((product.sustainLevel.toInt().toFloat()/10)))
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(green)
                ){
                }
            }
            Text(
                text = "Sustainability Level",
                color = pinkMera,
                fontSize = 12.sp,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            )
        }
    }
}