package com.dicoding.aplikasiku.allboom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.dicoding.aplikasiku.allboom.ui.AllBoomApp
import com.dicoding.aplikasiku.allboom.ui.theme.AllBoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllBoomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BackHandler {
                        onBackPressed()
                    }
                    AllBoomApp(title = "AllBoom", onBackPressed = ::onBackPressed)
                }
            }
        }
    }
}