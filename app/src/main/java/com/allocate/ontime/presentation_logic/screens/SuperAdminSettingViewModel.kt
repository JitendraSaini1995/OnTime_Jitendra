package com.allocate.ontime.presentation_logic.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allocate.ontime.business_logic.data.DataOrException
import com.allocate.ontime.presentation_logic.model.DeviceInfo
import com.allocate.ontime.presentation_logic.model.ResponsePacket
import com.allocate.ontime.business_logic.repository.OnTimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperAdminSettingViewModel @Inject constructor(private val repository: OnTimeRepository) :
    ViewModel() {
//    val data: MutableState<DataOrException<DeviceInfo, Boolean, Exception>> =
//        mutableStateOf(
//            DataOrException(null, true, Exception(""))
//        )
//
//
//    init {
//        getAllDeviceInfo()
//    }
//
//    private fun getAllDeviceInfo() {
//        viewModelScope.launch {
//            data.value.loading = true
//            data.value.data = repository.getDeviceInfo().data
//            if (data.value.data.toString().isNotEmpty()) {
//                data.value.loading = false
//            }
//        }
//    }
suspend fun getDeviceData()
        : DataOrException<DeviceInfo, Boolean, Exception> {
    return repository.getDeviceInfo()

}

}