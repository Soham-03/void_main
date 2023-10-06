package com.example.hackmania.bottom_navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(){
    val navController = rememberNavController()
//    val state by viewModel.state.collectAsState()
    var show by remember {
        mutableStateOf(true)
    }
    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = !show) {
                BottomBar(navController = navController)
            }

        }
    ) {it.calculateBottomPadding()
        BottomNavigationGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Events,
        BottomBarScreen.Leaderboard,
        BottomBarScreen.Blogs
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Box(){
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(100.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(100.dp))
                .background(Yellow),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){

        }
    }
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(100.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(100.dp))
            .background(LightBlue),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        screens.forEach { screen->
            if(currentDestination!=null){
                AddItem(screen = screen, currentDestination =currentDestination , navController = navController)
            }
        }
    }
}