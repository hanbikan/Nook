package com.hanbikan.nooknook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme
import com.hanbikan.nooknook.ui.NnApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NnTheme {
                NnApp()
            }
        }
    }
}