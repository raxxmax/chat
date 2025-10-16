# 💬 Real-Time Chat Application

A modern Android chat application built with Jetpack Compose, Firebase Authentication, and Supabase Storage for seamless real-time messaging and image sharing.

## ✨ Features

- **Real-Time Messaging**: Send and receive messages instantly
- **Image Sharing**: Capture and share photos directly from the camera
- **User Authentication**: Secure sign-in and sign-up with Firebase Auth
- **Modern UI**: Beautiful, responsive interface built with Jetpack Compose
- **Cloud Storage**: Image uploads powered by Supabase Storage
- **Smooth Animations**: Fluid screen transitions and navigation

## 🛠️ Tech Stack

### Core Technologies
- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern declarative UI framework
- **Firebase Authentication** - User authentication and management
- **Supabase Storage** - Cloud storage for images
- **Dagger Hilt** - Dependency injection

### Architecture & Libraries
- **MVVM Architecture** - Clean separation of concerns
- **Coroutines** - Asynchronous programming
- **Navigation Component** - Type-safe navigation
- **Material Design 3** - Modern design system

## 📱 Screenshots

| Splash Screen | Sign In Screen | Sign Up Screen | Home Screen | Chat Interface |
|:--------------:|:--------------:|:--------------:|:------------:|:---------------:|
![chat](https://github.com/user-attachments/assets/0b6a2037-d692-4cba-8622-f282b8ed6f28) | ![chat](https://github.com/user-attachments/assets/2c035e3a-51ae-4684-ab43-1572f659c9bf) | ![chat](https://github.com/user-attachments/assets/31f85cc3-d3d7-463e-99dc-4a17c840c533) | ![chat](https://github.com/user-attachments/assets/53f46a50-71d2-407c-8989-9746e536cdc9)
 | ![chat](https://github.com/user-attachments/assets/52fc6a69-7395-408f-a938-e5d0b708ff3a) | ![chat](https://github.com/user-attachments/assets/4fb11df4-6f7d-40b4-979c-f8bee9806dae)


## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17 or higher
- Android SDK (API 21+)

### Firebase Setup

1. Create a new project in [Firebase Console](https://console.firebase.google.com/)
2. Add an Android app to your Firebase project
3. Download `google-services.json` and place it in the `app/` directory
4. Enable Authentication in Firebase Console (Email/Password provider)

### Supabase Setup

1. Create a project in [Supabase](https://supabase.com/)
2. Create a storage bucket named `chat_image`
3. Set the bucket to **public** access
4. Update the Supabase credentials in `SupabaseStorageUtil.kt`:
   ```kotlin
   val supabase = createSupabaseClient(
       "YOUR_SUPABASE_URL",
       "YOUR_SUPABASE_ANON_KEY"
   ) {
       install(Storage)
   }
   ```

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/chat-application.git
   cd chat-application
   ```

2. Open the project in Android Studio

3. Sync Gradle files

4. Run the app on an emulator or physical device

## 📂 Project Structure

```
com.example.chat/
├── ChatApplication.kt          # Application class with Hilt setup
├── MainActivity.kt             # Main activity with Compose setup
├── MainAppCompose.kt          # Navigation graph and routing
├── SupabaseStorageUtil.kt     # Image upload utility
├── feature/
│   ├── auth/
│   │   ├── signin/           # Sign-in screen
│   │   └── signup/           # Sign-up screen
│   ├── home/                 # Home/chat list screen
│   ├── chat/                 # Chat messaging screen
│   └── SplashScreen.kt       # Splash screen
└── ui/
    └── theme/                 # App theming
```

## 🔐 Security Note

**Important**: The current code contains hardcoded Supabase credentials. Before deploying to production:

1. Move all API keys and secrets to `local.properties`
2. Use BuildConfig to access credentials securely
3. Add `local.properties` to `.gitignore`
4. Never commit sensitive credentials to version control

Example secure implementation:
```kotlin
// In build.gradle.kts
buildConfigField("String", "SUPABASE_URL", "\"${project.properties["supabase.url"]}\"")
buildConfigField("String", "SUPABASE_KEY", "\"${project.properties["supabase.key"]}\"")
```

## 🎯 Usage

1. **Sign Up**: Create a new account with email and password
2. **Sign In**: Log in with your credentials
3. **Start Chatting**: Select a chat channel from the home screen
4. **Send Messages**: Type and send text messages
5. **Share Images**: Tap the camera icon to capture and share photos

## 📝 Image Upload Flow

```
User taps camera icon
    ↓
Camera launches and captures image
    ↓
Image uploaded to Supabase Storage
    ↓
Public URL generated
    ↓
Message sent with image URL
    ↓
Image displayed in chat
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 👥 Author

- Your Name - [@raxxmax](https://github.com/raxxmax)

## 🙏 Acknowledgments

- Firebase team for excellent authentication services
- Supabase team for reliable cloud storage
- Android team for Jetpack Compose

## 📧 Contact

For questions or support, please reach out to: raahulshaarmaa28@gmail.com

---

⭐ Star this repo if you find it helpful!
