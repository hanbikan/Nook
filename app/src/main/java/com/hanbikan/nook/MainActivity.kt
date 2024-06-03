package com.hanbikan.nook

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.feature.tutorial.navigation.welcomeScreenRoute
import com.hanbikan.nook.ui.NkApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.updateUserIfLanguageHasChanged()

        setContent {
            NkTheme {
                val lastVisitedRoute = viewModel.lastVisitedRoute.collectAsStateWithLifecycle().value
                val isReady = viewModel.isReady.collectAsStateWithLifecycle().value
                if (isReady) {
                    NkApp(startDestination = lastVisitedRoute ?: welcomeScreenRoute)
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(NkTheme.colorScheme.background)
                    ) {}
                }
            }
        }

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (viewModel.isReady.value) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.
                        false
                    }
                }
            }
        )
    }
}