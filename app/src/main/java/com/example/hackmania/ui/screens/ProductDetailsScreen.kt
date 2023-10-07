package com.example.hackmania.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.hackmania.CartActivity
import com.example.hackmania.R
import com.example.hackmania.model.Global
import com.example.hackmania.model.Product
import com.example.hackmania.ui.theme.green
import com.example.hackmania.ui.theme.greyDark
import com.example.hackmania.ui.theme.greyLight
import com.example.hackmania.ui.theme.lightpeach
import com.example.hackmania.ui.theme.offWhite
import com.example.hackmania.ui.theme.peach
import com.example.hackmania.ui.theme.pinkMera
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun ProductDetailsScreen(product: Product){
//    val systemUiController =

    // Change the status bar color
//    systemUiController.setStatusBarColor(Color.Red)
    val db = FirebaseFirestore.getInstance()
    var availableInCart by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit){
        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("cart")
            .get()
            .addOnSuccessListener {
                for(doc in it.documents){
                    if(doc.id == product.productId){
                        availableInCart = true
                    }
                }
            }
            .addOnFailureListener {
                availableInCart = false
                Toast.makeText(context, "ILLLEEE", Toast.LENGTH_SHORT).show()
            }
    }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(offWhite)
    ){
        val context = LocalContext.current
        Box(){
            AsyncImage(
                model = product.productImage,
                contentDescription = "image product",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(12.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ){
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier
                        .clickable {
                        }
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(12.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .align(Alignment.TopEnd)
            ){
                Icon(
                    Icons.Rounded.ShoppingCart,
                    contentDescription = "Arrow Back",
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(context, CartActivity::class.java)
                            context.startActivity(intent)
                        }
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(bottom = 2.dp)
                .fillMaxWidth()
                .shadow(2.dp, clip = true, spotColor = green)
                .zIndex(1f)
        ){
            Column(
            ){
                Text(
                    text = product.productName,
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 2.dp)
                )
                Text(
                    text = "By ${product.companyName}",
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 2.dp)
                )
                Text(
                    text = product.price+" Rs",
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 2.dp)
                )
            }
            var selectedFavourite by remember {
                mutableStateOf(false)
            }
            Icon(
                if (selectedFavourite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = "",
                modifier = Modifier
                    .clickable {
                        selectedFavourite = !selectedFavourite
                    }
                    .padding(12.dp)
                    .size(40.dp)
            )
        }
        Text(
            text = "Product Details",
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 2.dp)
        )

        Text(
            text = product.productDescp,
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 2.dp)
        )

        Box(modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 12.dp)
            .fillMaxWidth()
            .height(2.dp)
            .background(greyDark)
        )

        Text(
            text = "Eco-Rating",
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 2.dp)
        )
        //sustain
        Text(
            text = "Sustainability Level",
            color = greyDark,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 12.dp)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, peach, RoundedCornerShape(10.dp))
                .background(Color.White),
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth(
                        ((product.sustainLevel
                            .toInt()
                            .toFloat() / 10))
                    )
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(green)
            ){
            }
        }
        //envImpact
        Text(
            text = "Impact on Environment",
            color = greyDark,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 12.dp)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, peach, RoundedCornerShape(10.dp))
                .background(Color.White),
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth(
                        ((product.envImpact
                            .toInt()
                            .toFloat() / 10))
                    )
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(pinkMera)
            ){
            }
        }
        //packaging
        Text(
            text = "Packaging and Waste Reduction",
            color = greyDark,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 12.dp)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, peach, RoundedCornerShape(10.dp))
                .background(Color.White),
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth(
                        ((product.packagingAndWasteReduction
                            .toInt()
                            .toFloat() / 10))
                    )
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(lightpeach)
            ){
            }
        }
        Text(
            text = "Carbon Emission",
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 2.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ){
            Text(
                text = "You'll leave ${product.carbonEmission} Footprints\nif you purchase this product.",
                color = greyDark,
                fontSize = 18.sp,
            )
            Image(
                painter = painterResource(id = R.drawable.foot),
                contentDescription = "",
                colorFilter = ColorFilter.tint(peach),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(52.dp)
            )
        }
        Text(
            text = "we reward you, if you help us save the planet :).",
            color = pinkMera,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        )
        var cartText by remember {
            mutableStateOf("Add to Cart")
        }
        if(availableInCart){
            cartText = "Added to Cart"
        }
        else{
            cartText = "Add to Cart"
        }
        Button(onClick = {
            if(availableInCart){
                Toast.makeText(context, "Already Added to Cart", Toast.LENGTH_SHORT).show()
            }
            else{
                val hashMap = HashMap<String, Any>()
                hashMap["productName"] = product.productName
                hashMap["productImage"] = product.productImage
                hashMap["companyName"] = product.companyName
                hashMap["price"] = product.price
                db.collection("users")
                    .document(FirebaseAuth.getInstance().currentUser!!.uid)
                    .collection("cart")
                    .document(product.productId)
                    .set(hashMap)
                    .addOnSuccessListener {
                        cartText = "Added to Cart"
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to add to cart", Toast.LENGTH_SHORT).show()
                    }
            }
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = peach,
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = cartText)
        }
    }
}