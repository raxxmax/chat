package com.example.chat.feature.home

import com.example.chat.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chat.model.Channel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewmodel = hiltViewModel<HomeViewmodel>()
    val channels = viewmodel.channels.collectAsState()
    val addChannel = remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()

    Scaffold (
        topBar = {
            TopAppBar(
                modifier = Modifier.height(140.dp),
                title = {
                    Column(verticalArrangement = Arrangement.Center ,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Messages",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        TextField(
                            value = "",
                            onValueChange = {},
                            placeholder = {
                                Text(
                                    "Search",
                                    fontSize = 15.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            textStyle = TextStyle(color = Color.Black),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =   Color(0xFF6198EE)
                )
            )
        },

        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(2.dp , 5.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF2A3D5F))
                    .clickable {
                        addChannel.value = true
                    }
                    .size(70.dp) ,
                   contentAlignment = Alignment.Center ,

            ){

             Image(painter = painterResource(R.drawable.addbutton) , "null" ,
                 modifier = Modifier.size(60.dp))
            }

        }

    ){

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White)
     ) {

            LazyColumn {

                items(channels.value) { channel: Channel ->
                    Column(modifier = Modifier.padding(0.dp , 2.dp))
                    {
                        channelItem(channel.name )
                        {
                            navController.navigate("chat/${channel.id}/${channel.name}")
                        }


                    }
                }
            }
            if(addChannel.value){
                ModalBottomSheet(onDismissRequest = {
                    addChannel.value = false
                } , sheetState = sheetState) {
                    AddChannelDialog {
                        viewmodel.addChannel(it)
                        addChannel.value = false
                    }
                }
            }
        }
    }
}
@Composable
fun channelItem(channelName: String , onClick : ()-> Unit){

    val randomColor = Color(
        red = Random.nextFloat(),
        green = Random.nextFloat(),
        blue = Random.nextFloat(),
        alpha = 0.4f
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(93.dp)
            .clip(RoundedCornerShape(11.dp))
            .background(Color(0x6F4E4B4B))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .padding(5.dp)
                .size(65.dp)
                .clip(CircleShape)
                .background(randomColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                channelName[0].toString(),
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFFF8F5F8)
            )
        }
        Text(channelName , modifier = Modifier.padding(8.dp),
            fontSize = 25.sp , color = Color.Black)
    }
}

@Composable
fun AddChannelDialog(onAddChannel : (String)-> Unit) {
    val channelName = remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier.padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Add Channel")

            Spacer(modifier = Modifier.padding(3.dp))

            TextField(
                value = channelName.value,
                onValueChange = {
                    channelName.value = it
                }, label = { Text("Channel Name") }, singleLine = true)

            Spacer(modifier = Modifier.padding(5.dp))

            Button(onClick = { onAddChannel(channelName.value) },
                modifier = Modifier.fillMaxWidth()) {

                Text("Add")
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen() {
    channelItem("Test channel" , {})
}