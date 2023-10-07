package com.example.hackmania.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hackmania.ui.screens.CartScreen
import com.example.hackmania.ui.screens.HomeScreen
import com.example.hackmania.ui.screens.ProductsScreen
import com.example.hackmania.ui.screens.ProfileScreen
import com.example.hackmania.ui.screens.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun BottomNavigationGraph(
    navController: NavHostController
){
//    LaunchedEffect(Unit){
//        viewModel.getAllEvents()
//        viewModel.getBestOfMonth()
////        viewModel.getLeaderboardTop10()
//        viewModel.getFlagshipEvents()
//        viewModel.getProblemStatement()
//    }
//    val state by viewModel.state.collectAsState()
    var show by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit){
        delay(1000)
        show = false
    }
    if(show){
        SplashScreen()
    }
    else{
        NavHost(navController = navController, startDestination = BottomBarScreen.Home.route){
            composable(route = BottomBarScreen.Home.route){
                HomeScreen()
            }
            composable(route = BottomBarScreen.Products.route){
                ProductsScreen()
            }
            composable(route = BottomBarScreen.Profile.route){
                ProfileScreen()
            }
//        composable(route = "EventInfo"){
//            EventInfoScreen(eventName = "", eventDate = "", eventTime = "","","",)
//        }
        }
    }

}