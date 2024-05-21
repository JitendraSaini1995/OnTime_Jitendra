package com.allocate.ontime.presentation_logic.navigation

import com.allocate.ontime.business_logic.utils.ScreenRoutes

sealed class OnTimeScreens(val name: String) {
    object SplashScreen : OnTimeScreens(ScreenRoutes.SPLASH_SCREEN)
    object HomeScreen : OnTimeScreens(ScreenRoutes.HOME_SCREEN)
    object AdminScreen : OnTimeScreens(ScreenRoutes.ADMIN_SCREEN)
    object SuperAdminScreen : OnTimeScreens(ScreenRoutes.SUPER_ADMIN_SCREEN)
    object AdminRegistrationScreen : OnTimeScreens(ScreenRoutes.ADMIN_REGISTRATION_SCREEN)
    object FobRegisterScreen : OnTimeScreens(ScreenRoutes.FOB_REGISTER_SCREEN)
    object SuperAdminSettingScreen : OnTimeScreens(ScreenRoutes.SUPER_ADMIN_SETTING_SCREEN)
    object VisitorRegistrationScreen : OnTimeScreens(ScreenRoutes.VISITOR_REGISTRATION_SCREEN)
    object FobImageScreen : OnTimeScreens(ScreenRoutes.FOB_IMAGE_SCREEN)

}

sealed class HomeScreenRoot {
    object AdminScreen : HomeScreenRoot()
    object SuperAdminScreen : HomeScreenRoot()
    object HomeScreen : HomeScreenRoot()
}

sealed class SuperAdminScreenRoot {
    object HomeScreen : SuperAdminScreenRoot()
    object AdminRegistrationScreen : SuperAdminScreenRoot()
    object VisitorRegistrationScreen : SuperAdminScreenRoot()
    object FobRegisterScreen : SuperAdminScreenRoot()
    object SuperAdminSettingScreen : SuperAdminScreenRoot()
    object SuperAdminScreen : SuperAdminScreenRoot()
    object FobImageScreen : SuperAdminScreenRoot()
}