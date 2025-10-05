# Chat App

A modern Android chat application built with Jetpack Compose, Firebase, Dagger Hilt, and Coil.

## Dependencies Added

### ✅ Firebase

- Firebase BOM v33.5.1
- Firebase Authentication
- Firebase Firestore
- Firebase Storage
- Firebase Analytics

### ✅ Dagger Hilt

- Hilt Android v2.48
- Hilt Navigation Compose v1.1.0

### ✅ Coil

- Coil Compose v2.5.0 (for image loading)

### ✅ Material 3

- Already configured with Compose BOM

### ✅ Navigation

- Navigation Compose v2.8.4

## Setup Instructions

### 1. Firebase Setup

1. Go to the [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or select an existing one
3. Add an Android app with package name: `com.example.chat`
4. Download the `google-services.json` file
5. Replace the template `app/google-services.json` with your actual file

### 2. Enable Hilt (Optional)

To enable Dagger Hilt dependency injection:

1. In `app/build.gradle.kts`, add the kapt processor:

```kotlin
kapt("com.google.dagger:hilt-android-compiler:2.48")
```

2. In `ChatApplication.kt`, add the annotation:

```kotlin
@HiltAndroidApp
class ChatApplication : Application()
```

3. In `MainActivity.kt`, add the annotation:

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // ...
}
```

### 3. Firebase Services

Enable the Firebase services you want to use in the Firebase Console:

- Authentication (Email/Password, Google, etc.)
- Firestore Database
- Storage
- Analytics

## Project Structure

```
app/
├── src/main/java/com/example/chat/
│   ├── ChatApplication.kt          # Application class
│   ├── MainActivity.kt             # Main activity
│   └── ui/theme/                   # Theme files
├── google-services.json            # Firebase configuration
└── build.gradle.kts               # App-level dependencies
```

## Key Features Ready to Implement

- 🔐 User Authentication with Firebase Auth
- 💬 Real-time messaging with Firestore
- 🖼️ Image sharing with Firebase Storage & Coil
- 🎨 Material 3 Design System
- 🧩 Dependency Injection with Hilt
- 📱 Modern UI with Jetpack Compose

## Next Steps

1. Set up Firebase project and replace the template configuration
2. Implement authentication screens
3. Create chat UI components
4. Set up Firestore data models
5. Implement real-time messaging
6. Add image upload/display functionality