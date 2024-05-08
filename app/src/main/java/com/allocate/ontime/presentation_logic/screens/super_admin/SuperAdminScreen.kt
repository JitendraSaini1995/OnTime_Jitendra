package com.allocate.ontime.presentation_logic.screens.super_admin

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.allocate.ontime.R
import com.allocate.ontime.business_logic.utils.OnTimeColors
import com.allocate.ontime.business_logic.viewmodel.super_admin.SuperAdminViewModel
import com.allocate.ontime.presentation_logic.navigation.SuperAdminScreenRoot
import com.allocate.ontime.presentation_logic.theme.dimens

@SuppressLint("RememberReturnType")
@Composable
fun SuperAdminScreen(
    superAdminScreenRoot: (SuperAdminScreenRoot) -> Unit,
    superAdminViewModel: SuperAdminViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()
    Log.d("jitu", "Super Admin Screen")

    val hasNoUserInteractionSuperAdminScreen = superAdminViewModel.navigationFlow.collectAsState()
    Log.d(
        "AutoBackNavigationManager",
        "SuperAdminScreen: ${hasNoUserInteractionSuperAdminScreen.value}"
    )
    if (hasNoUserInteractionSuperAdminScreen.value) {
        superAdminScreenRoot(SuperAdminScreenRoot.HomeScreen)
        superAdminViewModel.resetAutoBack()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        superAdminViewModel.startInteraction()
                    }
                )

            },
        color = OnTimeColors.TORY_BLUE
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.superAdminScrColTopPad)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.SUPER_ADMIN_PAGE),
                style = MaterialTheme.typography.headlineMedium,
                color = OnTimeColors.GREEN_HAZE,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = OnTimeColors.GREEN_HAZE),
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .size(
                        width = MaterialTheme.dimens.switchToMobileBtnW,
                        height = MaterialTheme.dimens.switchToMobileBtnH
                    )
                    .padding(end = MaterialTheme.dimens.switchToMobileBtnEndPad),
                shape = RoundedCornerShape(MaterialTheme.dimens.superAdminScrBtnCornerSz)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.switch_to))
                    Text(text = stringResource(id = R.string.mobile_mode))
                }

            }
            Spacer(modifier = Modifier.weight(MaterialTheme.dimens.superAdminScrSpacerWtBelowSwitchBtn))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.superAdminScrRowStartPad,
                        end = MaterialTheme.dimens.superAdminScrRowEndPad
                    )
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.superAdminScrIconsRowHArrangementSpacedBy)

            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            superAdminScreenRoot(SuperAdminScreenRoot.AdminRegistrationScreen)
                        }
                        .padding(
                            start = MaterialTheme.dimens.superAdminScrRowStartPad,
                            end = MaterialTheme.dimens.superAdminScrRowEndPad
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = stringResource(id = R.string.new_user_registration_img),
                        modifier = Modifier.size(MaterialTheme.dimens.superAdminScrIconsImgSize)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH10))
                    Text(
                        text = stringResource(id = R.string.New_or_Edit_Admin),
                        color = OnTimeColors.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(id = R.string.Registration),
                        color = OnTimeColors.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            superAdminScreenRoot(SuperAdminScreenRoot.VisitorRegistrationScreen)
                        }
                        .padding(
                            start = MaterialTheme.dimens.superAdminScrRowStartPad,
                            end = MaterialTheme.dimens.superAdminScrRowEndPad
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.visitor_icon),
                        contentDescription = stringResource(id = R.string.new_user_registration_img),
                        modifier = Modifier.size(MaterialTheme.dimens.superAdminScrIconsImgSize)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH10))
                    Text(
                        text = stringResource(id = R.string.New_or_Edit),
                        color = OnTimeColors.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(id = R.string.Visitor_Registration),
                        color = OnTimeColors.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            superAdminScreenRoot(SuperAdminScreenRoot.FobRegisterScreen)
                        }
                        .padding(
                            start = MaterialTheme.dimens.superAdminScrRowStartPad,
                            end = MaterialTheme.dimens.superAdminScrRowEndPad
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.fob_register),
                        contentDescription = stringResource(id = R.string.fob_register_img),
                        modifier = Modifier.size(MaterialTheme.dimens.superAdminScrIconsImgSize)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH10))
                    Text(
                        text = stringResource(id = R.string.FOB_Register),
                        color = OnTimeColors.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            superAdminScreenRoot(SuperAdminScreenRoot.SuperAdminSettingScreen)
                        }
                        .padding(
                            start = MaterialTheme.dimens.superAdminScrRowStartPad,
                            end = MaterialTheme.dimens.superAdminScrRowEndPad
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.device_setting_icon),
                        contentDescription = stringResource(id = R.string.device_setting_img),
                        modifier = Modifier.size(MaterialTheme.dimens.superAdminScrIconsImgSize)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH10))
                    Text(
                        text = stringResource(id = R.string.Device_Setting),
                        color = OnTimeColors.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        start = MaterialTheme.dimens.superAdminScrRowStartPad,
                        end = MaterialTheme.dimens.superAdminScrRowEndPad
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.deregister_admin_icon),
                        contentDescription = stringResource(id = R.string.deregister_admin_img),
                        modifier = Modifier.size(MaterialTheme.dimens.superAdminScrIconsImgSize)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH10))
                    Text(
                        text = "Deregister Admins",
                        color = OnTimeColors.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        start = MaterialTheme.dimens.superAdminScrRowStartPad,
                        end = MaterialTheme.dimens.superAdminScrRowEndPad
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.choose_logo_icon),
                        contentDescription = stringResource(id = R.string.choose_logo_img),
                        modifier = Modifier.size(MaterialTheme.dimens.superAdminScrIconsImgSize)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH10))
                    Text(
                        text = stringResource(id = R.string.Choose_Logo),
                        color = OnTimeColors.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        start = MaterialTheme.dimens.superAdminScrRowStartPad,
                        end = MaterialTheme.dimens.superAdminScrRowEndPad
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.download_database),
                        contentDescription = stringResource(id = R.string.export_db_img),
                        modifier = Modifier.size(MaterialTheme.dimens.superAdminScrIconsImgSize)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH5))
                    Text(
                        text = stringResource(id = R.string.Export_DB),
                        color = OnTimeColors.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Spacer(modifier = Modifier.weight(MaterialTheme.dimens.superAdminScrSpacerWeightBelowIconsRow))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.Fingerprint_Match_Rate),
                    color = OnTimeColors.White,
                )
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerW20))
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(MaterialTheme.dimens.superAdminScrBtnCornerSz),
                    modifier = Modifier.size(
                        width = MaterialTheme.dimens.fingerprintMatchRateBtnW,
                        height = MaterialTheme.dimens.fingerprintMatchRateBtnH
                    ),
                    colors = ButtonDefaults.buttonColors(containerColor = OnTimeColors.LightPink),
                    border = BorderStroke(
                        width = MaterialTheme.dimens.superAdminScrBtnBorderWidth,
                        color = OnTimeColors.White
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.Fingerprint_Match_Rate),
                        color = OnTimeColors.DarkGray
                    )
                }
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerW20))
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(MaterialTheme.dimens.superAdminScrBtnCornerSz),
                    colors = ButtonDefaults.buttonColors(containerColor = OnTimeColors.GREEN_HAZE),
                    modifier = Modifier.size(
                        width = MaterialTheme.dimens.superAdminScrSubmitBtnW,
                        height = MaterialTheme.dimens.superAdminScrSubmitBtnH
                    )
                ) {
                    Text(text = stringResource(id = R.string.SUBMIT))
                }
            }
            Spacer(modifier = Modifier.weight(MaterialTheme.dimens.superAdminScrSpacerWeightBelowSubmitBtn))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.dimens.superAdminScrBottomRowBottomPad),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        superAdminScreenRoot(SuperAdminScreenRoot.HomeScreen)
                    },
                    shape = RoundedCornerShape(MaterialTheme.dimens.superAdminScrBtnCornerSz),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OnTimeColors.GREEN_HAZE, contentColor = OnTimeColors.White
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.btnRowHArrangementSpacedBy),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.circle_black),
                            contentDescription = stringResource(id = R.string.circle_black_img),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(MaterialTheme.dimens.circleBlkImgSz)
                        )
                        Text(
                            text = stringResource(id = R.string.Log_out_and_return_to_home_page),
                        )
                    }
                }
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerW20))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(MaterialTheme.dimens.superAdminScrBtnCornerSz),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OnTimeColors.GREEN_HAZE, contentColor = OnTimeColors.White
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.btnRowHArrangementSpacedBy),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.circle_green),
                            contentDescription = stringResource(id = R.string.circle_green_img),
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(color = OnTimeColors.MANTIS),
                            modifier = Modifier.size(MaterialTheme.dimens.circleGrnImgSz)
                        )
                        Text(
                            text = stringResource(id = R.string.View_Employee_Online),
                        )
                    }
                }
            }
        }
    }
}