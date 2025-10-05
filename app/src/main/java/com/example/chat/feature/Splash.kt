package com.example.chat.feature

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen( navController: NavController ,
) {

    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha"
    )

    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val dotAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "dots"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1500) // Splash delay

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // user logged in → Go to Home
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true } // remove splash from backstack
            }
        } else {
            // user not logged in → Go to Sign In
            navController.navigate("signin") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF7274EE),
                        Color(0xFFDED783)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.alpha(alphaAnim.value)
        ) {
            // Logo Container - Place your logo here
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scaleAnim.value * pulseScale)
                    .background(
                        color = Color.Transparent.copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                 Image(painter = painterResource(R.drawable.logoo),  contentDescription = "Logo"
                )


            }

            Spacer(modifier = Modifier.height(32.dp))

            // App Name
            Text(
                text = "Chat",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.scale(scaleAnim.value)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Connect with everyone",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.scale(scaleAnim.value)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Loading dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.scale(scaleAnim.value)
            ) {
                repeat(3) { index ->
                    val delay = index * 0.2f
                    val dotAlpha = when {
                        dotAnimation < delay -> 0.3f
                        dotAnimation < delay + 0.3f -> 1f
                        else -> 0.3f
                    }

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .alpha(dotAlpha)
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}