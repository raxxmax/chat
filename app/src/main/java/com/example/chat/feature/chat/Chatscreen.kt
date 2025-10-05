package com.example.chat.feature.chat

import android.Manifest
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.chat.R
import com.example.chat.model.Message
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.util.Date
import java.util.Locale as JavaLocale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun chatScreen(navController: NavController, channelID: String, channelName: String) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserName = currentUser?.displayName ?: "Anonymous"

    val topBarTitle = when {
        channelName.isNotEmpty() -> channelName
        else -> "Chat"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column (verticalArrangement = Arrangement.Center ,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(topBarTitle , fontWeight = FontWeight.Bold , fontSize = 35.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =   Color(0xFF6198EE)
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(painter = painterResource(R.drawable.backk), contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        val context = LocalContext.current
        val viewModel: ChatViewModel = hiltViewModel()

        val chooserDialog = remember {
            mutableStateOf(false)
        }

        val cameraImageUri : MutableState<Uri?> = remember {
           mutableStateOf(null)
        }

        fun createImageUri(): Uri {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", JavaLocale.getDefault()).format(Date())
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
            val imageUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                imageFile
            )
            cameraImageUri.value = imageUri
            return imageUri
        }

        val cameraImageLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->
            if (success) {
                Log.d("Camera", "Image captured successfully")
                cameraImageUri.value?.let {
                    viewModel.sendImageMessage(it, channelID)
                }
            } else {
                Log.d("Camera", "Image capture failed")
            }
        }
        val GalleryImageLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri : Uri? ->

            uri?.let{ viewModel.sendImageMessage(it, channelID)}

            }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Log.d("Camera", "Camera permission granted")
                cameraImageLauncher.launch(createImageUri())
            } else {
                Log.d("Camera", "Camera permission denied")
            }
        }

        LaunchedEffect(true) {
            viewModel.getListenMessages(channelID)
        }

        val messages = viewModel.messages.collectAsState()

        Image(
            painter = painterResource(id = R.drawable.newbgg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.1f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            chatMessages(
                messages = messages.value,
                onSendMessage = { message ->
                    viewModel.sendMessages(channelID, message)
                },
                onImageIsClicked = {
                    chooserDialog.value = true
                }
            )
        }

        if (chooserDialog.value) {
            ContentSelectDialog(
                onCameraSelect = {
                    chooserDialog.value = false
                    if (context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        Log.d("Camera", "Launching camera")
                        cameraImageLauncher.launch(createImageUri())
                    } else {
                        Log.d("Camera", "Requesting camera permission")
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                onGallerySelect = {
                    chooserDialog.value = false
                    GalleryImageLauncher.launch("image/*")
                }
            )
        }
    }
}

@Composable
fun ContentSelectDialog( onCameraSelect:()-> Unit , onGallerySelect :()-> Unit){
    AlertDialog(onDismissRequest = {},

        confirmButton = { TextButton(
            onClick = onCameraSelect
        ) { Text("Camera")}},

        dismissButton = {TextButton(
            onClick = onGallerySelect
        ) { Text("Gallery")}},

        title = {Text("Select your Source")},
        text = {Text("Pick from Gallery or use Camera")})

}

@Composable
fun chatMessages(
    messages: List<Message>,
    onSendMessage: (String) -> Unit ,
    onImageIsClicked: () -> Unit
) {
    val msg = remember {
        mutableStateOf("")
    }

    val hidekeyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(messages) { message ->
                ChatBubble(message = message)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.Transparent)
                .clip(RoundedCornerShape(10.dp)),
            verticalAlignment = Alignment.CenterVertically

        ) {
            IconButton( modifier = Modifier.clip(shape = CutCornerShape(10.dp)) ,
                onClick = {
                    onImageIsClicked()
                })
            {
                Image(painter = painterResource (R.drawable.attachement), contentDescription = "send")
            }
            TextField(shape = RoundedCornerShape(10.dp),
                value = msg.value,
                onValueChange = { newValue: String -> msg.value = newValue },
                placeholder = { Text(text = "Message  ") },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp)),

                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hidekeyboardController?.hide()
                    }
                )  ,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE3EEF5),
                    unfocusedContainerColor = Color(0xFF6090D9),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            IconButton( modifier = Modifier.clip(shape = CutCornerShape(10.dp)) ,
                onClick = {
                    if (msg.value.isNotBlank()) {
                        onSendMessage(msg.value.trim())
                        msg.value = ""
                    }
                })
            {
                Image(painter = painterResource (R.drawable.send_iconn), contentDescription = "send")
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    val isCurrentUser = message.senderId == FirebaseAuth.getInstance().currentUser?.uid
    val bubbleColor = if (isCurrentUser) {
        Color(0xFF548AF8)
    } else {
        Color(0xFF59CB5D)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        // Show sender name for other users
        if (!isCurrentUser && message.senderName.isNotEmpty()) {
            Text(
                text = message.senderName,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(start = 8.dp, bottom = 2.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
            , verticalAlignment = Alignment.CenterVertically
        ) {
            if (isCurrentUser) {
                chats(message)
                Profile()

            } else {

                Profile()
                chats(message)
            }

        }
    }
}

@Composable
fun Profile(){
    Image( painter = painterResource(R.drawable.profile) ,
        contentDescription = "profile picture" ,
        modifier = Modifier.size(40.dp))


}

@Composable
fun chats(message: Message){
    val isCurrentUser = message.senderId == FirebaseAuth.getInstance().currentUser?.uid
    val bubbleColor = if (isCurrentUser) {
        Color(0xFF36508B)
    } else {
        Color(0xFF378038) // Green for other users
    }

    Box(
        modifier = Modifier
            .background(
                color = bubbleColor,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isCurrentUser) 16.dp else 4.dp,
                    bottomEnd = if (isCurrentUser) 4.dp else 16.dp
                )
            )
            .padding(16.dp)
    ){
if(message.imageUrl != null){

    AsyncImage(
        model = message.imageUrl
        , contentDescription = null ,
        modifier = Modifier.size(250.dp)
    )
    
}
else {
    Text(
        text = message.message?.trim() ?: " ",
        color = Color.White,
        style = MaterialTheme.typography.headlineSmall,

    )
}
}
}

