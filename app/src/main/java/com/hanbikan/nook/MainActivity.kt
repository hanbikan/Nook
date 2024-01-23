package com.hanbikan.nook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.usecase.GetAllUsersUseCase
import com.hanbikan.nook.ui.NkApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getAllUsersUseCase: GetAllUsersUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NkTheme {
                NkApp(
                    getAllUsersUseCase = getAllUsersUseCase,
                )
            }
        }
    }
}