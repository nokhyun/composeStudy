package com.example.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.composables.BottomNavigationRippleItem
import com.example.ui.composables.NotosansText
import com.example.ui.font.notosanskr
import com.example.ui.font.textDp

class ComposeNavigationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MainScreen(navController)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(
        navController: NavHostController
    ) {
        Scaffold(
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                androidx.compose.material.BottomNavigation(
                    backgroundColor = Color.White,
                    contentColor = Color.Blue
                ) {
                    bottomNavItems.forEach { item ->
//                            BottomNavigationItem(
                        BottomNavigationRippleItem(
                            label = {
                                NotosansText(
                                    text = stringResource(id = item.title),
                                    fontWeight = FontWeight.Light,
                                    fontSize = 14
                                )
                            },
                            icon = {
                                Icon(
                                    painterResource(id = item.icon),
                                    contentDescription = null
                                )
                            },
                            selectedContentColor = Color.Blue,
                            unselectedContentColor = Color.Gray,
                            alwaysShowLabel = true,
                            selected = currentDestination?.hierarchy?.any { it.route == item.screenRoute } == true,
                            onClick = {
                                navController.navigate(item.screenRoute) {
                                    popUpTo(navController.graph.findStartDestination().id)

                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }) {
            Box(modifier = Modifier.padding(it)) {
                Navigation(navController = navController)
            }
        }
    }

    @Composable
    fun Navigation(
        navController: NavHostController
    ) {
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.screenRoute
        ) {
            composable(BottomNavItem.Home.screenRoute) {
                HomeScreen()
            }

            composable(BottomNavItem.Second.screenRoute) {
                SecondScreen()
            }
        }
    }

    sealed class BottomNavItem(
        @StringRes val title: Int, @DrawableRes val icon: Int, val screenRoute: String
    ) {
        object Home : BottomNavItem(R.string.home, R.drawable.ic_home, "HOME")
        object Second : BottomNavItem(R.string.second, R.drawable.ic_home, "SECOND")
    }

    @Composable
    fun HomeScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NotosansText(
                text = "HomeScreen",
                fontWeight = FontWeight.Bold,
                fontSize = 28
            )
        }
    }

    @Composable
    fun SecondScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NotosansText(
                text = "SecondScreen",
                fontWeight = FontWeight.Light,
                fontSize = 28
            )

        }
    }

    companion object {
        private val bottomNavItems = listOf(
            BottomNavItem.Home,
            BottomNavItem.Second
        )
    }
}