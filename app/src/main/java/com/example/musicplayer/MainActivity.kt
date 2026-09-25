package com.example.musicplayer // Sesuaikan dengan package project Anda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Panggil tampilan musik dari file MusicPlayerScreen.kt
            MusicPlayerScreen()
        }
    }
}