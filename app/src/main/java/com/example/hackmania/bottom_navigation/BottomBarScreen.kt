package com.example.hackmania.bottom_navigation

import com.example.hackmania.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
){
    object Home: BottomBarScreen(
        route = "home",
        title = "home",
        icon = R.drawable.home
    )

    object Products: BottomBarScreen(
        route = "products",
        title = "products",
        icon = R.drawable.gift
    )

    object Profile: BottomBarScreen(
        route = "profile",
        title = "profile",
        icon = R.drawable.user
    )

    object Cart: BottomBarScreen(
        route = "cart",
        title = "cart",
        icon = R.drawable.cart
    )

//    object Blogs: BottomBarScreen(
//        route = "blogs",
//        title = "blogs",
//        icon = R.drawable.ic_blogs
//    )
//    object EventInfo: BottomBarScreen(
//        route = "eventInfo",
//        title = "eventInfo",
//        icon = R.drawable.ic_events
//    )
}