package com.example.hackmania.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hackmania.R
import com.example.hackmania.model.Cart
import com.example.hackmania.model.Product
import com.example.hackmania.ui.theme.green
import com.example.hackmania.ui.theme.greyDark
import com.example.hackmania.ui.theme.offWhite
import com.example.hackmania.ui.theme.peach
import com.example.hackmania.ui.theme.pinkMera
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.annotations.Async

@Composable
fun ProfileScreen(){
    val user = FirebaseAuth.getInstance().currentUser
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(offWhite)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Profile",
            color = Color.Black,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(start = 12.dp)
                .align(Alignment.Start)
        )
        AsyncImage(
            model = user!!.photoUrl,
            contentDescription = "profile image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            error = painterResource(id = R.drawable.ic_launcher_background),
        )

        Text(
            text = user.displayName.toString(),
            color = greyDark,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
        )
        Text(
            text = user.email.toString(),
            color = greyDark,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        var listOfProductsPurchased by remember {
            mutableStateOf(ArrayList<Cart>())
        }

        val lists = ArrayList<Cart>()
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("cart")
            .get()
            .addOnSuccessListener {
                val docs = it.documents
                for(doc in docs){
                    lists.add(
                        Cart(
                            productName = doc["productName"].toString(),
                            productImage = doc["productImage"].toString(),
                            price = doc["price"].toString(),
                            companyName = doc["companyName"].toString()
                        )
                    )
                }
                listOfProductsPurchased = lists
            }
        var carbonFootprints by remember {
            mutableStateOf("")
        }
        var wallet by remember {
            mutableStateOf("")
        }
        LaunchedEffect(key1 = Unit){
            db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .get()
                .addOnSuccessListener {
                    carbonFootprints = it["carbonFoot"].toString()
                    wallet = it["wallet"].toString()
                }

        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = green
            ),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ){
                Image(
                    painter = painterResource(id = R.drawable.foot),
                    contentDescription = "",
                    modifier = Modifier
                        .size(52.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Carbon FootPrints",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = carbonFootprints+" COe",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = pinkMera
            ),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ){
                Image(
                    painter = painterResource(id = R.drawable.wallet),
                    contentDescription = "",
                    modifier = Modifier
                        .size(52.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Wallet",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = wallet+" Rs",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = "History",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
                .height(400.dp)
        ){
            for(product in listOfProductsPurchased){
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFCCCCCC)),
                        modifier = Modifier
                            .fillMaxWidth()
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
                                text = product.price,
                                color = Color.Black,
                                fontSize = 18.sp
                            )
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
            }
        }
        Spacer(modifier = Modifier.height(100.dp))

    }
}