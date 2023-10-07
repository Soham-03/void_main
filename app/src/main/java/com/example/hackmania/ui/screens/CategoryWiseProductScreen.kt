package com.example.hackmania.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackmania.model.Category
import com.example.hackmania.model.Global
import com.example.hackmania.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@Composable
fun CategoryWiseProductScreen(){
    Column {
        val context = LocalContext.current
        val db = FirebaseFirestore.getInstance()
        Row(
            modifier = Modifier
                .padding(12.dp)
        ){
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .clickable {

                    }
            )
            Text(
                text = Global.category!!.title,
                color = Color.Black,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
        }
        val productList = ArrayList<Product>()
        var productsList by remember {
            mutableStateOf(ArrayList<Product>())
        }
        db.collection("categories").document(Global.category!!.id)
            .collection("products")
            .orderBy("sustainLvl",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {it->
                val docs = it.documents
                for(doc in docs){
                    productList.add(
                        Product(
                            productId = doc.id.toString(),
                            productName = doc["productName"].toString(),
                            productImage = doc["productImage"].toString(),
                            productDescp = doc["productDescp"].toString(),
                            price = doc["price"].toString(),
                            rating = doc["rating"].toString(),
                            companyName = doc["companyName"].toString(),
                            sustainLevel = doc["sustainLvl"].toString(),
                            packagingAndWasteReduction = doc["packagingAndWasteRed"].toString(),
                            envImpact = doc["envImpact"].toString(),
                            carbonEmission = doc["carbonEmission"].toString(),
                            rewards = doc["rewards"].toString()
                        )
                    )
                }
                productsList = productList
            }
        Text("Products sorted by sustainability level", modifier = Modifier.padding(start = 12.dp))
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(12.dp)
        ){
            for(product in productsList){
                item {
                    ProductCard(product = product)
                }
            }
        }
    }
}