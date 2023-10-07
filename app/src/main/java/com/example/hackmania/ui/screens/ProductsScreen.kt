package com.example.hackmania.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hackmania.ProductsActivity
import com.example.hackmania.R
import com.example.hackmania.model.Category
import com.example.hackmania.model.Global
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun ProductsScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFF4))
    ){
        val context = LocalContext.current
        val db = FirebaseFirestore.getInstance()
        Text(
            text = "Categories",
            color = Color.Black,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(12.dp)
        )
        var categoriesList by remember {
            mutableStateOf(ArrayList<Category>())
        }
        LaunchedEffect(key1 = Unit){
            val tempList = ArrayList<Category>()
            db.collection("categories").get().addOnSuccessListener {it->
                val docs = it.documents
                for(doc in docs){
                    tempList.add(Category(doc.id.toString(), doc["categoryName"].toString(), doc["categoryImage"].toString()))
                }
                categoriesList = tempList
                println("GG:"+categoriesList)
            }.await()
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            for(category in categoriesList){
                item {
                    AsyncImage(
                        model = category.image,
                        contentDescription = "categories",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable {
                                Global.category = category
                                val intent = Intent(context, ProductsActivity::class.java)
                                context.startActivity(intent)
                            }
                    )
                }
            }
        }
    }
}