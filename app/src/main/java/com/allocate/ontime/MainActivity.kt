package com.allocate.ontime

import android.annotation.SuppressLint
import android.app.admin.DevicePolicyManager
import android.app.admin.SystemUpdatePolicy
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.os.UserManager
import android.provider.Settings
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.allocate.ontime.presentation_logic.navigation.OnTimeNavigation
import com.allocate.ontime.presentation_logic.theme.OnTimeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private lateinit var mAdminComponentName: ComponentName
//    private lateinit var mDevicePolicyManager: DevicePolicyManager

    companion object {
        const val LOCK_ACTIVITY_KEY = "com.allocate.ontime.MainActivity"
    }


    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnTimeApp()
        }

//        mAdminComponentName = MyDeviceAdminReceiver.getComponentName(this)
//        mDevicePolicyManager =
//            getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
//
//        mDevicePolicyManager.removeActiveAdmin(mAdminComponentName)
//
//        val isAdmin = isAdmin()
//
//        if (mDevicePolicyManager.isDeviceOwnerApp(packageName)) {
//            setKioskPolicies(true, isAdmin)
//        } else {
////            setKioskPolicies(false, isAdmin)
////            val intent = Intent(applicationContext, MainActivity::class.java).apply {
////                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////            }
////            intent.putExtra(LOCK_ACTIVITY_KEY, false)
////            startActivity(intent)
//        }
    }

//    private fun isAdmin() = mDevicePolicyManager.isDeviceOwnerApp(packageName)
//
//    private fun setKioskPolicies(enable: Boolean, isAdmin: Boolean) {
//        if (isAdmin) {
//            setRestrictions(enable)
//            enableStayOnWhilePluggedIn(enable)
//            setUpdatePolicy(enable)
//            setAsHomeApp(enable)
//            setKeyGuardEnabled(enable)
//        }
//        setLockTask(enable, isAdmin)
//        setImmersiveMode(enable)
//    }
//
//    // region restrictions
//    private fun setRestrictions(disallow: Boolean) {
//        setUserRestriction(UserManager.DISALLOW_SAFE_BOOT, disallow)
//        setUserRestriction(UserManager.DISALLOW_FACTORY_RESET, disallow)
//        setUserRestriction(UserManager.DISALLOW_ADD_USER, disallow)
//        setUserRestriction(UserManager.DISALLOW_MOUNT_PHYSICAL_MEDIA, disallow)
//        setUserRestriction(UserManager.DISALLOW_ADJUST_VOLUME, disallow)
//        mDevicePolicyManager.setStatusBarDisabled(mAdminComponentName, disallow)
//    }
//
//    private fun setUserRestriction(restriction: String, disallow: Boolean) = if (disallow) {
//        mDevicePolicyManager.addUserRestriction(mAdminComponentName, restriction)
//    } else {
//        mDevicePolicyManager.clearUserRestriction(mAdminComponentName, restriction)
//    }
//    // endregion
//
//    private fun enableStayOnWhilePluggedIn(active: Boolean) = if (active) {
//        mDevicePolicyManager.setGlobalSetting(
//            mAdminComponentName,
//            Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
//            (BatteryManager.BATTERY_PLUGGED_AC
//                    or BatteryManager.BATTERY_PLUGGED_USB
//                    or BatteryManager.BATTERY_PLUGGED_WIRELESS).toString()
//        )
//    } else {
//        mDevicePolicyManager.setGlobalSetting(
//            mAdminComponentName,
//            Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
//            "0"
//        )
//    }
//
//    private fun setLockTask(start: Boolean, isAdmin: Boolean) {
//        if (isAdmin) {
//            mDevicePolicyManager.setLockTaskPackages(
//                mAdminComponentName, if (start) arrayOf(packageName) else arrayOf()
//            )
//        }
//        if (start) {
//            startLockTask()
//        } else {
//            stopLockTask()
//        }
//    }
//
//    private fun setUpdatePolicy(enable: Boolean) {
//        if (enable) {
//            mDevicePolicyManager.setSystemUpdatePolicy(
//                mAdminComponentName,
//                SystemUpdatePolicy.createWindowedInstallPolicy(60, 120)
//            )
//        } else {
//            mDevicePolicyManager.setSystemUpdatePolicy(mAdminComponentName, null)
//        }
//    }
//
//    private fun setAsHomeApp(enable: Boolean) {
//        if (enable) {
//            val intentFilter = IntentFilter(Intent.ACTION_MAIN).apply {
//                addCategory(Intent.CATEGORY_HOME)
//                addCategory(Intent.CATEGORY_DEFAULT)
//            }
//            mDevicePolicyManager.addPersistentPreferredActivity(
//                mAdminComponentName,
//                intentFilter,
//                ComponentName(packageName, MainActivity::class.java.name)
//            )
//        } else {
//            mDevicePolicyManager.clearPackagePersistentPreferredActivities(
//                mAdminComponentName, packageName
//            )
//        }
//    }
//
//    private fun setKeyGuardEnabled(enable: Boolean) {
//        mDevicePolicyManager.setKeyguardDisabled(mAdminComponentName, !enable)
//    }
//
//    @Suppress("DEPRECATION")
//    private fun setImmersiveMode(enable: Boolean) {
//        if (enable) {
//            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
//            window.decorView.systemUiVisibility = flags
//        } else {
//            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//            window.decorView.systemUiVisibility = flags
//        }
//    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OnTimeApp() {
    OnTimeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnTimeNavigation()
            }
        }
    }
}


