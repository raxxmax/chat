package com.example.chat

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chat.feature.SplashScreen
import com.example.chat.feature.auth.signin.SignInScreen
import com.example.chat.feature.auth.signup.SignUpScreen
import com.example.chat.feature.chat.chatScreen
import com.example.chat.feature.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth
@Composable
fun MainApp(){
    Surface (modifier = Modifier.fillMaxSize()){
        val navController = rememberNavController()


        NavHost(navController = navController, startDestination = "Splash") {
            composable(
               "Splash"
            ) {
                SplashScreen(navController)
            }
            composable("signin" ,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }, // Start from right
                        animationSpec = tween(300) // 300ms duration
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }, // Exit to left
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth }, // Come back from left
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth }, // Exit to right
                        animationSpec = tween(300)
                    )
                }
            ) {
                SignInScreen(navController)
            }



            composable("signup",
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }, // Start from right
                        animationSpec = tween(300) // 300ms duration
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }, // Exit to left
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth }, // Come back from left
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth }, // Exit to right
                        animationSpec = tween(300)
                    )
                }
            ) {
                SignUpScreen(navController)
            }


            composable("home",
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }, // Start from right
                        animationSpec = tween(300) // 300ms duration
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }, // Exit to left
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth }, // Come back from left
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth }, // Exit to right
                        animationSpec = tween(300)
                    )
                }
            ) {
                HomeScreen(navController)
            }

            composable(
                "chat/{channelID}/{senderName}",
                arguments = listOf(
                    navArgument("channelID"){
                      type =  NavType.StringType
                    },
                    navArgument("senderName") {
                        type = NavType.StringType
                    }
                ) ,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }, // Start from right
                        animationSpec = tween(300) // 300ms duration
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }, // Exit to left
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth }, // Come back from left
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth }, // Exit to right
                        animationSpec = tween(300)
                    )
                }

            ){
                val channelID = it.arguments?.getString("channelID") ?: ""
                val senderName = it.arguments?.getString("senderName") ?: ""
                chatScreen(navController, channelID, senderName)
            }

        }
    }

}