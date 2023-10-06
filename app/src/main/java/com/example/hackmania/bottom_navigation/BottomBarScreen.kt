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
        icon = R.drawable.ic_home
    )

    object Events: BottomBarScreen(
        route = "events",
        title = "events",
        icon = R.drawable.ic_events
    )

    object Leaderboard: BottomBarScreen(
        route = "leaderboard",
        title = "leaderboard",
        icon = R.drawable.ic_leaderboard
    )

    object Blogs: BottomBarScreen(
        route = "blogs",
        title = "blogs",
        icon = R.drawable.ic_blogs
    )
    object EventInfo: BottomBarScreen(
        route = "eventInfo",
        title = "eventInfo",
        icon = R.drawable.ic_events
    )
}