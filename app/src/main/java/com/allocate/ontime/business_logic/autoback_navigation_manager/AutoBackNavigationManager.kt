package com.allocate.ontime.business_logic.autoback_navigation_manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AutoBackNavigationManager @Inject constructor() {

    private val navigateScreens: HashMap<String, Long> = hashMapOf()
    private val navigationFlowInternal = MutableStateFlow(false)
    var navigationFlow = navigationFlowInternal.asStateFlow()
    private var timeoutJob: Job? = null
    private val TIMEOUT_DURATION_MS = 60000L

    fun addOrUpdateScreens(screen: String) {
        navigateScreens[screen] = System.currentTimeMillis()
        startTimeout(screen)
    }

    fun removeScreens(screen: String) {
        navigateScreens.remove(screen)
        navigationFlowInternal.value = false
    }

    private fun startTimeout(screenName: String) {
        stopTimeout()
        timeoutJob = CoroutineScope(Dispatchers.IO).launch {
            if (isActive) {
                delay(TIMEOUT_DURATION_MS)
                checkScreenInteractions(screenName)
            }
        }
    }

    private fun checkScreenInteractions(screenName: String) {
        val currentTime = System.currentTimeMillis()
        if (navigateScreens.isNotEmpty()) {
            val screenLastTime = navigateScreens[screenName]
            screenLastTime?.let { screenLastTimes ->
                if (currentTime - screenLastTimes >= TIMEOUT_DURATION_MS) {
                    navigationFlowInternal.value = true
                }
            }
        } else {
            navigationFlowInternal.value = false
        }
    }

    private fun stopTimeout() {
        timeoutJob?.cancel()
    }
}



