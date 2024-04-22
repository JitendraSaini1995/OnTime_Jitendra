package com.allocate.ontime.presentation_logic.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.allocate.ontime.business_logic.utils.LogMsg
import com.allocate.ontime.presentation_logic.screens.super_admin.AdminRegistrationScreen
import com.allocate.ontime.presentation_logic.screens.admin.AdminScreen
import com.allocate.ontime.presentation_logic.screens.super_admin.FobRegisterScreen
import com.allocate.ontime.presentation_logic.screens.home.HomeScreen
import com.allocate.ontime.presentation_logic.screens.super_admin.SuperAdminScreen
import com.allocate.ontime.presentation_logic.screens.super_admin.SuperAdminSettingScreen
import com.allocate.ontime.presentation_logic.screens.splash.SplashScreen
import com.allocate.ontime.presentation_logic.screens.super_admin.VisitorRegistrationScreen


//@ExperimentalComposeUiApi
//@Composable
//fun OnTimeNavigation() {
//    val navController = rememberNavController()
//    val tag = "Navigation"
//    NavHost(
//        navController = navController,
//        startDestination = OnTimeScreens.SplashScreen.name
//    ) {
//        composable(OnTimeScreens.HomeScreen.name) {
//            HomeScreen(
//                homeScreenRoot = {
//                    when (it) {
//                        HomeScreenRoot.AdminScreen -> navController.navigate(OnTimeScreens.AdminScreen.name)
//                        HomeScreenRoot.SuperAdminScreen -> navController.navigate(OnTimeScreens.SuperAdminScreen.name)
//                        HomeScreenRoot.HomeScreen -> navController.navigate(OnTimeScreens.HomeScreen.name)
//                    }
//                }
//            )
//        }
//        composable(OnTimeScreens.SuperAdminScreen.name) {
//            SuperAdminScreen(
//                superAdminScreenRoot = {
//                    when (it) {
//                        SuperAdminScreenRoot.AdminRegistrationScreen -> navController.navigate(
//                            OnTimeScreens.AdminRegistrationScreen.name
//                        )
//
//                        SuperAdminScreenRoot.VisitorRegistrationScreen -> navController.navigate(
//                            OnTimeScreens.VisitorRegistrationScreen.name
//                        )
//
//                        SuperAdminScreenRoot.FobRegisterScreen -> navController.navigate(
//                            OnTimeScreens.FobRegisterScreen.name
//                        )
//
//                        SuperAdminScreenRoot.SuperAdminSettingScreen -> navController.navigate(
//                            OnTimeScreens.SuperAdminSettingScreen.name
//                        )
//
//                        SuperAdminScreenRoot.HomeScreen -> navController.navigate(
//                            OnTimeScreens.HomeScreen.name
//                        )
//
//                        SuperAdminScreenRoot.SuperAdminScreen -> navController.navigate(
//                            OnTimeScreens.SuperAdminScreen.name
//                        )
//                    }
//                })
//        }
//        composable(OnTimeScreens.AdminScreen.name) {
//            AdminScreen(backToHome = {
//                when (it) {
//                    HomeScreenRoot.HomeScreen -> navController.navigate(OnTimeScreens.HomeScreen.name)
//                    else -> {
//                        Log.i(tag, LogMsg.NAVIGATION_GETS_WRONG)
//                    }
//                }
//            })
//        }
//        composable(OnTimeScreens.AdminRegistrationScreen.name) {
//            AdminRegistrationScreen(backToSuperAdminScreen = {
//                when (it) {
//                    SuperAdminScreenRoot.SuperAdminScreen -> navController.navigate(OnTimeScreens.SuperAdminScreen.name)
//                    else -> {
//                        Log.i(tag, LogMsg.NAVIGATION_GETS_WRONG)
//                    }
//                }
//            })
//        }
//        composable(OnTimeScreens.VisitorRegistrationScreen.name) {
//            VisitorRegistrationScreen(backToSuperAdminScreen = {
//                when (it) {
//                    SuperAdminScreenRoot.SuperAdminScreen -> navController.navigate(OnTimeScreens.SuperAdminScreen.name)
//                    else -> {
//                        Log.i("Navigation", "Navigation gets wrong.")
//                    }
//                }
//            })
//        }
//        composable(OnTimeScreens.FobRegisterScreen.name) {
//            FobRegisterScreen(backToSuperAdminScreen = {
//                when (it) {
//                    SuperAdminScreenRoot.SuperAdminScreen -> navController.navigate(OnTimeScreens.SuperAdminScreen.name)
//                    else -> {
//                        Log.i(tag, LogMsg.NAVIGATION_GETS_WRONG)
//                    }
//                }
//            })
//        }
//        composable(OnTimeScreens.SuperAdminSettingScreen.name) {
//            SuperAdminSettingScreen(backToSuperAdminScreen = {
//                when (it) {
//                    SuperAdminScreenRoot.SuperAdminScreen -> navController.navigate(OnTimeScreens.SuperAdminScreen.name)
//                    else -> {
//                        Log.i(tag, LogMsg.NAVIGATION_GETS_WRONG)
//                    }
//                }
//            })
//        }
//        composable(OnTimeScreens.SplashScreen.name) {
//            SplashScreen(homeScreenRoot = {
//                when (it) {
//                    HomeScreenRoot.HomeScreen -> navController.navigate(OnTimeScreens.HomeScreen.name)
//                    else -> {
//                        Log.i(tag, LogMsg.NAVIGATION_GETS_WRONG)
//                    }
//                }
//            })
//        }
//    }
//}

@ExperimentalComposeUiApi
@Composable
fun OnTimeNavigation() {
    val navController = rememberNavController()
    val tag = "Navigation"

    fun NavController.popUpTo(destination: String) = navigate(destination) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        restoreState = true
    }


    val homeScreensRoot: (HomeScreenRoot) -> Unit = remember {
        {
            when (it) {
                HomeScreenRoot.AdminScreen -> navController.popUpTo(OnTimeScreens.AdminScreen.name)
                HomeScreenRoot.SuperAdminScreen -> navController.popUpTo(OnTimeScreens.SuperAdminScreen.name)
                HomeScreenRoot.HomeScreen -> navController.popUpTo(OnTimeScreens.HomeScreen.name)
                else -> {
                    Log.i(tag, LogMsg.NAVIGATION_GETS_WRONG)
                }
            }
        }
    }

    val superAdminScreenRoot: (SuperAdminScreenRoot) -> Unit = remember {
        {
            when (it) {
                SuperAdminScreenRoot.AdminRegistrationScreen -> navController.popUpTo(
                    OnTimeScreens.AdminRegistrationScreen.name
                )

                SuperAdminScreenRoot.VisitorRegistrationScreen -> navController.popUpTo(
                    OnTimeScreens.VisitorRegistrationScreen.name
                )

                SuperAdminScreenRoot.FobRegisterScreen -> navController.popUpTo(
                    OnTimeScreens.FobRegisterScreen.name
                )

                SuperAdminScreenRoot.SuperAdminSettingScreen -> navController.popUpTo(
                    OnTimeScreens.SuperAdminSettingScreen.name
                )

                SuperAdminScreenRoot.HomeScreen -> navController.popUpTo(
                    OnTimeScreens.HomeScreen.name
                )

                SuperAdminScreenRoot.SuperAdminScreen -> navController.popUpTo(
                    OnTimeScreens.SuperAdminScreen.name
                )
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = OnTimeScreens.SplashScreen.name
    ) {
        composable(OnTimeScreens.HomeScreen.name) {
            HomeScreen(homeScreenRoot = homeScreensRoot)
        }
        composable(OnTimeScreens.SuperAdminScreen.name) {
            SuperAdminScreen(superAdminScreenRoot = superAdminScreenRoot)
        }
        composable(OnTimeScreens.AdminScreen.name) {
            AdminScreen(backToHome = homeScreensRoot)
        }
        composable(OnTimeScreens.AdminRegistrationScreen.name) {
            AdminRegistrationScreen(backToSuperAdminScreen = superAdminScreenRoot)
        }
        composable(OnTimeScreens.VisitorRegistrationScreen.name) {
            VisitorRegistrationScreen(backToSuperAdminScreen =  superAdminScreenRoot)
        }
        composable(OnTimeScreens.FobRegisterScreen.name) {
            FobRegisterScreen(backToSuperAdminScreen = superAdminScreenRoot)
        }
        composable(OnTimeScreens.SuperAdminSettingScreen.name) {
            SuperAdminSettingScreen(backToSuperAdminScreen = superAdminScreenRoot)
        }
        composable(OnTimeScreens.SplashScreen.name) {
            SplashScreen(homeScreenRoot = homeScreensRoot)
        }
    }
}