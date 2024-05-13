package com.allocate.ontime.business_logic.api_manager

import com.allocate.ontime.presentation_logic.model.CombinedResponse

interface OnTimeDatabaseManager {
    fun syncDataInDb(combinedResponse: CombinedResponse)
}