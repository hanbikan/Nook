package com.hanbikan.nook.feature.phone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NkBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var appStateRepository: AppStateRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        // 언어 변경 시 app state language 변경
        if (intent?.action == Intent.ACTION_LOCALE_CHANGED) {
            CoroutineScope(Dispatchers.IO).launch {
                context?.resources?.configuration?.locales?.get(0)?.language?.let { language ->
                    appStateRepository.setLanguage(language)
                }
            }
        }
    }
}