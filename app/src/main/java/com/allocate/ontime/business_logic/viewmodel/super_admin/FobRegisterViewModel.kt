package com.allocate.ontime.business_logic.viewmodel.super_admin

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.allocate.ontime.business_logic.autoback_navigation_manager.AutoBackNavigationManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allocate.ontime.R
import com.allocate.ontime.business_logic.api_manager.OnTimeDatabaseManagerImpl
import com.allocate.ontime.business_logic.data.room.entities.EmployeePacket
import com.allocate.ontime.presentation_logic.model.Employee
import com.allocate.ontime.presentation_logic.navigation.OnTimeScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FobRegisterViewModel @Inject constructor(
    private val autoBackNavigationManager: AutoBackNavigationManager,
    private val onTimeDatabaseManagerImpl: OnTimeDatabaseManagerImpl) :
    ViewModel() {
    val navigationFlow = autoBackNavigationManager.navigationFlow
    private val _searchResults = MutableStateFlow<List<EmployeePacket>>(emptyList())
    val searchResults: StateFlow<List<EmployeePacket>> = _searchResults
    val _isAllLocation  = MutableStateFlow(false)
    val isAllLocation = _isAllLocation.asStateFlow()
    val _fobVersion  = MutableStateFlow(0)
    val fobVersion = _fobVersion.asStateFlow()


    init {
        startInteraction()
        getEmployeeData()
//        getAllLocationBool()
    }

    fun getEmployeeData(){
        viewModelScope.launch(Dispatchers.IO){
            onTimeDatabaseManagerImpl.getEmployee().distinctUntilChanged().collect{ employeePacketList ->
                _searchResults.value = employeePacketList
                employeePacketList.forEach{empPktLst->
                    _isAllLocation.value =  empPktLst.allOffice
                    _fobVersion.value = empPktLst.fobVersion
                }
            }
        }
    }

//    private fun getAllLocationBool(){
//        viewModelScope.launch(Dispatchers.IO){
//            onTimeDatabaseManagerImpl.getEmployee().distinctUntilChanged().collect{ employeePacketList ->
//                employeePacketList.forEach{
//                    _isAllLocation.value =  it.allOffice
//                }
//            }
//        }
//    }





    fun startInteraction() {
        autoBackNavigationManager.addOrUpdateScreens(OnTimeScreens.FobRegisterScreen.name)
    }

    fun resetAutoBack() {
        autoBackNavigationManager.removeScreens(OnTimeScreens.FobRegisterScreen.name)
    }

}