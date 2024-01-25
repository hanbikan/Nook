package com.hanbikan.nook

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.usecase.GetLastVisitedRouteUseCase
import com.hanbikan.nook.feature.tutorial.navigation.welcomeScreenRoute
import com.hanbikan.nook.ui.NkApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getLastVisitedRouteUseCase: GetLastVisitedRouteUseCase

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 스플래시를 지연시키고, 그동안 GetLastVisitedRouteUseCase를 수행하여 자연스러운 화면을 유도합니다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.ALPHA,
                    1f,
                    0f
                ).apply {
                    duration = 300L
                    doOnEnd { splashScreenView.remove() }
                    start()
                }
            }
        }

        setContent {
            NkTheme {
                var isReady by remember { mutableStateOf(false) }
                var lastVisitedRoute: String? by remember { mutableStateOf(null) }

                LaunchedEffect(Unit) {
                    getLastVisitedRouteUseCase().collect {
                        isReady = true
                        lastVisitedRoute = it
                    }
                }

                if (!isReady) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(NkTheme.colorScheme.background)
                    ) {}
                } else {
                    NkApp(startDestination = lastVisitedRoute ?: welcomeScreenRoute)
                }
            }
        }
    }
}