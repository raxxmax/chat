package com.example.chat.feature.auth.signup

import android.R.attr.singleLine
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButtonDefaults.colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chat.R

@Composable
fun SignUpScreen(navController: NavController ) {
    val viewmodel: SignupViewmodel = hiltViewModel()
    val uiState = viewmodel.state.collectAsState()

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var Name by remember {
        mutableStateOf("")
    }
    var confirm by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val shape = RoundedCornerShape(12.dp)

    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is SignUpState.Success -> {
                navController.navigate("home")
            }
            is SignUpState.Error -> {
                Toast.makeText(
                    context,
                    (uiState.value as SignUpState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background( Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF7274EE),
                            Color(0xFFDED783)
                        )
                    )),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(45.dp))
                Image(
                    painter = painterResource(id = R.drawable.logoo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.Transparent)
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text("Welcome" ,
                    fontWeight = FontWeight.ExtraBold ,
                    fontSize = 45.sp)
                Text("Happy to see You , Sign up" ,
                    fontWeight = FontWeight.ExtraBold ,
                    fontSize = 15.sp ,
                    color = Color.White)

                Spacer(modifier = Modifier.padding(33.dp))

                OutlinedTextField(
                    value = Name,
                    onValueChange = { Name = it },
                    modifier = Modifier.fillMaxWidth().padding(24.dp , 0.dp),
                    placeholder = { Text("Name") },
                    label = { Text("Name") },
                        shape = shape ,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.DarkGray,
                            unfocusedLabelColor = Color.Gray
                        ),
                        singleLine = true

                )

                Spacer(modifier = Modifier.size(10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth().padding(24.dp , 0.dp),
                    placeholder = { Text("Email") },
                    label = { Text("Email") } ,
                    shape = shape ,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.DarkGray,
                        unfocusedLabelColor = Color.Gray
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.size(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth().padding(24.dp , 0.dp),
                    placeholder = { Text("Password") },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation() ,
                    shape = shape ,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.DarkGray,
                        unfocusedLabelColor = Color.Gray
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.size(10.dp))

                OutlinedTextField(
                    value = confirm,
                    onValueChange = { confirm = it },
                    modifier = Modifier.fillMaxWidth().padding(24.dp , 0.dp),
                    placeholder = { Text(" Confirm Password") },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = shape ,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.DarkGray,
                        unfocusedLabelColor = Color.Gray
                    ),
                    singleLine = true ,
                    isError = password.isNotEmpty() && password != confirm
                )

                Spacer(modifier = Modifier.size(25.dp))

                if (uiState.value == SignUpState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        onClick = {
                            viewmodel.SignUp(Name, email, password)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9100), // Background color
                            contentColor = Color.White, // Text/Icon color
                            disabledContainerColor = Color.Gray, // Background when disabled
                            disabledContentColor = Color.LightGray
                        ),
                        modifier = Modifier.fillMaxWidth().padding(24.dp , 0.dp),
                        enabled = Name.isNotEmpty() && email.isNotEmpty() &&
                                password.isNotEmpty() && confirm.isNotEmpty() && (password == confirm) && uiState.value != SignUpState.Loading
                    ) {
                        Text("Sign UP")
                    }
                    Spacer(modifier = Modifier.size(100.dp))
                    TextButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Text(
                            "Already have an account ? sign in ",
                            style = TextStyle(textDecoration = TextDecoration.Underline)
                        )
                    }
                }
            }
        }
    }


@Preview
@Composable
fun PreviewSignUP() {
    SignUpScreen(navController = rememberNavController())
}