package com.example.hackmania.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.savedstate.R
import coil.compose.AsyncImage
import com.example.hackmania.ProductsActivity
import com.example.hackmania.model.Category
import com.example.hackmania.model.Global
import com.example.hackmania.ui.theme.green
import com.example.hackmania.ui.theme.greyDark
import com.example.hackmania.ui.theme.greyLight
import com.example.hackmania.ui.theme.lightpeach
import com.example.hackmania.ui.theme.offWhite
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

@Composable
fun HomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFF4))
            .verticalScroll(rememberScrollState())
    )
    {
        val user = FirebaseAuth.getInstance().currentUser
        Text(
            text = "Hi, ${user!!.displayName}",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
        )
        val db = FirebaseFirestore.getInstance()
        var listOfCategory by remember {
            mutableStateOf(ArrayList<Category>())
        }
        val listOfCategories = ArrayList<Category>()
        LaunchedEffect(key1 = Unit){
            db.collection("categories")
                .get()
                .addOnSuccessListener {it->
                    val docs = it.documents
                    for(doc in docs){
                        listOfCategories.add(
                            Category(
                                doc.id.toString(),
                                doc["categoryName"].toString(),
                                doc["categoryImage"].toString()
                            )
                        )
                    }
                    listOfCategory = listOfCategories
                }
        }
        val content = LocalContext.current
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ){
            for(category in listOfCategory){
                item {
                    Column(
                        verticalArrangement = Arrangement.Center,
                    ){
                        AsyncImage(
                            model = category.image,
                            contentDescription = "Category",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .clickable {
                                    Global.category = category
                                    val intent = Intent(content, ProductsActivity::class.java)
                                    content.startActivity(intent)
                                }
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = category.title,
                            fontSize = 16.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier
                                .width(80.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clip(RoundedCornerShape(12.dp))
        ){
            Text(
                text = "Why Sustaibnable Products",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            )
            LazyRow(
                modifier = Modifier
                    .padding(8.dp)
            ){
                item{
                    AsyncImage(
                        model = "https://firebasestorage.googleapis.com/v0/b/hackmania-a64ff.appspot.com/o/WhatsApp%20Image%202023-10-07%20at%2012.34.37%20(1).jpeg?alt=media&token=b7e0f1d2-df58-4c53-93bc-b8e1ea6cc052&_gl=1*1gmb5qo*_ga*MTE2NjYzMTU5My4xNjc5NDYyNzky*_ga_CW55HF8NVT*MTY5NjY1NjMzNS40Ni4xLjE2OTY2NjI2NzYuNDAuMC4w",
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .width(180.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
                item{
                    AsyncImage(
                        model = "https://firebasestorage.googleapis.com/v0/b/hackmania-a64ff.appspot.com/o/WhatsApp%20Image%202023-10-07%20at%2012.34.37%20(2).jpeg?alt=media&token=5b891ff2-b914-4139-a425-a005204d3cda&_gl=1*1fqo6z4*_ga*MTE2NjYzMTU5My4xNjc5NDYyNzky*_ga_CW55HF8NVT*MTY5NjY1NjMzNS40Ni4xLjE2OTY2NjI3ODkuNjAuMC4w",
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .width(180.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
                item{
                    AsyncImage(
                        model = "https://firebasestorage.googleapis.com/v0/b/hackmania-a64ff.appspot.com/o/WhatsApp%20Image%202023-10-07%20at%2012.34.37%20(3).jpeg?alt=media&token=c6e06e98-087d-42ce-8d5e-1965301d9942&_gl=1*lp4uhs*_ga*MTE2NjYzMTU5My4xNjc5NDYyNzky*_ga_CW55HF8NVT*MTY5NjY1NjMzNS40Ni4xLjE2OTY2NjMwMjYuNTMuMC4w",
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .width(180.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
                item{
                    AsyncImage(
                        model = "https://firebasestorage.googleapis.com/v0/b/hackmania-a64ff.appspot.com/o/WhatsApp%20Image%202023-10-07%20at%2012.34.37.jpeg?alt=media&token=f896f6df-f322-4ee7-a57c-fc9f9af70581&_gl=1*1bz2i31*_ga*MTE2NjYzMTU5My4xNjc5NDYyNzky*_ga_CW55HF8NVT*MTY5NjY1NjMzNS40Ni4xLjE2OTY2NjMwNjkuMTAuMC4w",
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .width(180.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(lightpeach)
        ){
            Text(
                text = "Stories and More",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp, end = 8.dp)
            )
            LazyRow(
            ){
                item{
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                    ){
                        AsyncImage(
                            model = "https://www.prysmiangroup.com/sites/default/files/thumbnails/image/SUSTAINABILITY-GROWTH-1150X650.gif",
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(8.dp)
                                .width(180.dp)
                                .height(100.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Text(
                            text = "How bussiness get profit\nby saving mother earth",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        )
                    }
                }
                item{
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                    ){
                        AsyncImage(
                            model = "https://www.prysmiangroup.com/sites/default/files/thumbnails/image/SUSTAINABILITY-GROWTH-1150X650.gif",
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(8.dp)
                                .width(180.dp)
                                .height(100.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Text(
                            text = "How bussiness get profit\nby saving mother earth",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        )
                    }
                }
                item{
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                    ){
                        AsyncImage(
                            model = "https://www.prysmiangroup.com/sites/default/files/thumbnails/image/SUSTAINABILITY-GROWTH-1150X650.gif",
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(8.dp)
                                .width(180.dp)
                                .height(100.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Text(
                            text = "How bussiness get profit\nby saving mother earth",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        )
                    }
                }
                item{
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                    ){
                        AsyncImage(
                            model = "https://www.prysmiangroup.com/sites/default/files/thumbnails/image/SUSTAINABILITY-GROWTH-1150X650.gif",
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(8.dp)
                                .width(180.dp)
                                .height(100.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Text(
                            text = "How bussiness get profit\nby saving mother earth",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(offWhite)
        ) {
            Text(
                text = "What our users say",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp, end = 8.dp)
            )
            LazyRow(
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .background(greyLight)
                    ) {
                        Text(
                            text = "'Shopping at your sustainable marketplace has been a game-changer for me. I love the eco-friendly products and knowing that every purchase supports a greener future'",
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(20.dp)
                                .width(300.dp)
                                .wrapContentHeight()
                        )
                        Text(
                            text = "By Sarah",
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(20.dp)
                                .width(300.dp)
                                .wrapContentHeight()
                        )
                    }
                }
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .background(greyLight)
                    ) {
                        Text(
                            text = "'Shopping at your sustainable marketplace has been a game-changer for me. I love the eco-friendly products and knowing that every purchase supports a greener future'",
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(20.dp)
                                .width(300.dp)
                                .wrapContentHeight()
                        )
                        Text(
                            text = "By Sarah",
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(20.dp)
                                .width(300.dp)
                                .wrapContentHeight()
                        )
                    }
                }
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .background(greyLight)
                    ) {
                        Text(
                            text = "'Shopping at your sustainable marketplace has been a game-changer for me. I love the eco-friendly products and knowing that every purchase supports a greener future'",
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(20.dp)
                                .width(300.dp)
                                .wrapContentHeight()
                        )
                        Text(
                            text = "By Sarah",
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(20.dp)
                                .width(300.dp)
                                .wrapContentHeight()
                        )
                    }
                }
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .background(greyLight)
                    ) {
                        Text(
                            text = "'Shopping at your sustainable marketplace has been a game-changer for me. I love the eco-friendly products and knowing that every purchase supports a greener future'",
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(20.dp)
                                .width(300.dp)
                                .wrapContentHeight()
                        )
                        Text(
                            text = "By Sarah",
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(20.dp)
                                .width(300.dp)
                                .wrapContentHeight()
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}