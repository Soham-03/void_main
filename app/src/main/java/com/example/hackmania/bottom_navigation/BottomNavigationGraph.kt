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
import com.example.hackmania.ui.screens.HomeScreen
import com.example.hackmania.ui.screens.ProductsScreen
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
//    LaunchedEffect(Unit){
//        delay(3000)
//        if(!state.isFlagShipEventLoading){
//            show = false
//        }
//    }
    if(show){
        SplashScreen()
    }
    else{
        NavHost(navController = navController, startDestination = BottomBarScreen.Home.route){
            composable(route = BottomBarScreen.Home.route){
                HomeScreen(viewModel = viewModel)
            }
            composable(route = BottomBarScreen.Events.route){
                ProductsScreen(viewModel)
            }
            composable(route = BottomBarScreen.Leaderboard.route){
                LeaderBoardScreen(viewModel = viewModel)
            }
            composable(route = BottomBarScreen.Blogs.route){
                BlogsScreen()
            }
//        composable(route = "EventInfo"){
//            EventInfoScreen(eventName = "", eventDate = "", eventTime = "","","",)
//        }
        }
    }

}