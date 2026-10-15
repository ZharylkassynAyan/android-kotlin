package com.example.gamevault

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.gamevault.navigation.AppNavigation
import com.example.gamevault.ui.theme.GameVaultTheme

class MainActivity : ComponentActivity() {
    private val tag = "GameVault"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "Activity onCreate")
        enableEdgeToEdge()
        setContent {
            GameVaultTheme {
                AppNavigation()
            }
        }
    }

    override fun onStart() { super.onStart(); Log.d(tag, "Activity onStart") }
    override fun onResume() { super.onResume(); Log.d(tag, "Activity onResume") }
    override fun onPause() { super.onPause(); Log.d(tag, "Activity onPause") }
    override fun onStop() { super.onStop(); Log.d(tag, "Activity onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(tag, "Activity onDestroy") }
}