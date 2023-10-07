package com.example.hackmania

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hackmania.model.Cart
import com.example.hackmania.model.Product
import com.example.hackmania.ui.screens.CartScreen
import com.example.hackmania.ui.theme.HackManiaTheme
import com.example.hackmania.ui.theme.offWhite
import com.example.hackmania.ui.theme.peach
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.PaymentApp
import dev.shreyaspatil.easyupipayment.model.TransactionDetails

class CartActivity : ComponentActivity(), PaymentStatusListener {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HackManiaTheme {
                // A surface container using the 'background' color from the theme
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(offWhite)
                    )
                    {
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
                                text = "Cart",
                                color = Color.Black,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(start = 12.dp)
                            )
                        }
                        var cartList by remember {
                            mutableStateOf(ArrayList<Cart>())
                        }
                        var totalCartAmount by remember {
                            mutableIntStateOf(0)
                        }
                        var quantity by remember {
                            mutableIntStateOf(1)
                        }
                        var productPriceTotal by remember {
                            mutableIntStateOf(0)
                        }
                        val cartLists  = ArrayList<Cart>()
                        var priceList by remember {
                            mutableStateOf(ArrayList<Int>())
                        }
                        for(cart in cartList){
                            priceList.add(cart.price.toInt())
                        }
                        val db = FirebaseFirestore.getInstance()
                        var wallet by remember {
                            mutableStateOf("0")
                        }
                        LaunchedEffect(key1 = Unit){
                            db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)
                                .collection("cart")
                                .get()
                                .addOnSuccessListener {it->
                                    val docs = it.documents
                                    for(doc in docs){
                                        cartLists.add(
                                            Cart(
                                                productName = doc["productName"].toString(),
                                                productImage = doc["productImage"].toString(),
                                                price = doc["price"].toString(),
                                                companyName = doc["companyName"].toString()
                                            )
                                        )
                                    }
                                    cartList = cartLists
                                }

                            db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)
                                .get()
                                .addOnSuccessListener {
                                    wallet = it["wallet"].toString()
                                }

                        }
                        LazyColumn(
                            modifier = Modifier
                                .height(300.dp)
                        ){
                            for(i in 0 until cartList.size){
                                totalCartAmount += cartList[i].price.toInt()
                                item {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(20.dp),
                                        border = BorderStroke(1.dp, peach),
                                        modifier = Modifier
                                            .padding(8.dp)
                                    )
                                    {
                                        Row(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .fillMaxWidth()
                                        ){
                                            AsyncImage(
                                                model = cartList[i].productImage,
                                                contentDescription = "",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .width(100.dp)
                                                    .height(120.dp)
                                                    .clip(RoundedCornerShape(20.dp))
                                            )
                                            Column {
                                                Text(
                                                    text = cartList[i].companyName,
                                                    color = Color.Black,
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier
                                                        .padding(horizontal = 12.dp, vertical = 2.dp)
                                                )
                                                Text(
                                                    text = cartList[i].productName,
                                                    color = Color.Black,
                                                    fontSize = 18.sp,
                                                    modifier = Modifier
                                                        .padding(horizontal = 12.dp, vertical = 2.dp)
                                                )
                                                productPriceTotal = cartList[i].price.toInt()*quantity
                                                Text(
                                                    text = "$productPriceTotal /-",
                                                    color = Color.Black,
                                                    fontSize = 18.sp,
                                                    modifier = Modifier
                                                        .padding(horizontal = 12.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        val quant by remember {
                                            mutableIntStateOf(0)
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Image(Icons.Default.KeyboardArrowUp, "")
                                            Text(text = "Quantity: $quant")
                                            Image(Icons.Default.KeyboardArrowUp, "")
                                        }
                                    }
                                }
                            }
                        }
                        Text(
                            text = "Total Cart Amount: $totalCartAmount",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = "Wallet Amount: $wallet",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = "Payable Amount: ${totalCartAmount.toInt()-(wallet.toInt())}",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth()
                        )
                        var address by remember {
                            mutableStateOf(TextFieldValue(""))
                        }

                        OutlinedTextField(
                            value = address,
                            onValueChange = {
                                address = it
                            },
                            label = {
                                Text(text = "Put your address here..")
                            },
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        Button(onClick = {
                            if(address.text.isNotEmpty()){
                                val easyUpiPayment =
                                    EasyUpiPayment(this@CartActivity) {
                                        this.payeeVpa = "8600295685@upi"
                                        this.payeeName = "Sanket Mane"
                                        this.payeeMerchantCode = "12345"
                                        this.transactionId = "T2020090212345"
                                        this.transactionRefId = "T2020090212345"
                                        this.description = "Description"
                                        this.amount = (totalCartAmount.toInt()-200).toString()+".00"
                                        this.paymentApp = PaymentApp.ALL
                                    }

                                easyUpiPayment.startPayment()
                                easyUpiPayment.setPaymentStatusListener(this@CartActivity)
                            }
                        },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = peach,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "Proceed to Pay")
                        }
                    }
            }
        }
    }

    override fun onTransactionCancelled() {
        Toast.makeText(this@CartActivity, "Transaction Cancelled", Toast.LENGTH_SHORT).show()
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        Toast.makeText(this@CartActivity, "Transaction Completed", Toast.LENGTH_SHORT).show()
    }
}