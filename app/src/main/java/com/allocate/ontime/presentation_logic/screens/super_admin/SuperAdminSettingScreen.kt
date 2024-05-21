package com.allocate.ontime.presentation_logic.screens.super_admin

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.allocate.ontime.R
import com.allocate.ontime.business_logic.data.DataOrException
import com.allocate.ontime.business_logic.utils.OnTimeColors
import com.allocate.ontime.business_logic.viewmodel.super_admin.SuperAdminSettingViewModel
import com.allocate.ontime.presentation_logic.model.DeviceInfo
import com.allocate.ontime.presentation_logic.navigation.SuperAdminScreenRoot
import com.allocate.ontime.presentation_logic.theme.dimens
import com.allocate.ontime.presentation_logic.widgets.InputField

@SuppressLint("SuspiciousIndentation")
@Composable
fun SuperAdminSettingScreen(
    backToSuperAdminScreen: (SuperAdminScreenRoot) -> Unit,
    superAdminViewModel: SuperAdminSettingViewModel = hiltViewModel(),
) {
    val TAG = "SuperAdminSettingScreen"

    val checkBoxState = remember {
        mutableStateOf(false)
    }
    val trustState = remember {
        mutableStateOf("")
    }
    val locationState = remember {
        mutableStateOf("")
    }
    val postCodeState = remember {
        mutableStateOf("")
    }
    val uniqueIdentifierState = remember {
        mutableStateOf("")
    }
    val latitudeState = remember {
        mutableStateOf("")
    }
    val longitudeState = remember {
        mutableStateOf("")
    }
    val latLngState = remember {
        mutableStateOf("")
    }
    val siteNameState = remember {
        mutableStateOf("")
    }
    val isRLD = remember {
        mutableStateOf(false)
    }

    val deviceData = produceState<DataOrException<DeviceInfo, Exception>>(
        initialValue = DataOrException()
    ) {
        value = superAdminViewModel.getDeviceData()
    }.value

    if (deviceData.data?.statusCode == 200) {
        deviceData.data!!.responsePacket.forEach {
            trustState.value = it.TrustOrganization
            locationState.value = it.Location
            postCodeState.value = it.Postcode
            uniqueIdentifierState.value = it.Unique_Identifier
            latitudeState.value = it.Latitude
            longitudeState.value = it.Longitude
            siteNameState.value = it.SiteName
            isRLD.value = it.IsRLD
        }
        latLngState.value = latitudeState.value + ',' + longitudeState.value
    }

    val hasNoUserInteractionSuperAdminSettingScreen =
        superAdminViewModel.navigationFlow.collectAsState()

    if (hasNoUserInteractionSuperAdminSettingScreen.value) {
        backToSuperAdminScreen(SuperAdminScreenRoot.SuperAdminScreen)
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.Super_Admin_Setting),
                style = MaterialTheme.typography.headlineMedium,
                color = OnTimeColors.GREEN_HAZE,
                fontWeight = FontWeight.Bold
            )
            SuperAdminSettingInfo(
                trustState,
                locationState,
                postCodeState,
                uniqueIdentifierState,
                latLngState,
                siteNameState,
                isRLD
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.superAdminSettingScrRowHArrangementSpacedBy)
            ) {
                Text(
                    text = stringResource(id = R.string.Clock_In_or_Out),
                    color = OnTimeColors.White,
                    style = MaterialTheme.typography.titleSmall
                )
                Switch(
                    checked = true, onCheckedChange = {}, colors = SwitchDefaults.colors(
                        uncheckedThumbColor = OnTimeColors.White,
                        uncheckedTrackColor = OnTimeColors.LightGray,
                        checkedThumbColor = OnTimeColors.Cyan,
                        checkedTrackColor = OnTimeColors.White
                    )
                )
                Text(
                    text = stringResource(id = R.string.Swipe_And_Go),
                    color = OnTimeColors.White,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = { checkBoxState.value },
                    colors = CheckboxDefaults.colors(uncheckedColor = OnTimeColors.White)
                )
                Text(
                    text = stringResource(id = R.string.Enable_two_way_Authentication),
                    color = OnTimeColors.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.superAdminSettingScrRowHArrangementSpacedBy)
            ) {
                Text(
                    text = stringResource(id = R.string.Enable_Pin),
                    color = OnTimeColors.White,
                    style = MaterialTheme.typography.titleSmall
                )
                Switch(
                    checked = true, onCheckedChange = {}, colors = SwitchDefaults.colors(
                        uncheckedThumbColor = OnTimeColors.White,
                        uncheckedTrackColor = OnTimeColors.LightGray,
                        checkedThumbColor = OnTimeColors.Cyan,
                        checkedTrackColor = OnTimeColors.White
                    )
                )
                Text(
                    text = stringResource(id = R.string.Disable_Pin),
                    color = OnTimeColors.White,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(MaterialTheme.dimens.superAdminSettingScrBtnCornerSz),
                colors = ButtonDefaults.buttonColors(containerColor = OnTimeColors.GREEN_HAZE),
            ) {
                Text(text = stringResource(id = R.string.CHECK_FOR_UPDATE))
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH10))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.dimens.superAdminSettingScrBottomRowBottomPad),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        backToSuperAdminScreen(SuperAdminScreenRoot.SuperAdminScreen)
                    },
                    shape = RoundedCornerShape(MaterialTheme.dimens.superAdminSettingScrBtnCornerSz),
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
                        Text(text = stringResource(id = R.string.Click_here_to_go_back))
                    }
                }
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerW20))
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(MaterialTheme.dimens.superAdminSettingScrBtnCornerSz),
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
                        Text(text = stringResource(id = R.string.View_Employee_Online))
                    }
                }
            }
        }
    }
}

@Composable
private fun SuperAdminSettingInfo(
    trustState: MutableState<String>,
    locationState: MutableState<String>,
    postCodeState: MutableState<String>,
    uniqueIdentifierState: MutableState<String>,
    latLngState: MutableState<String>,
    siteNameState: MutableState<String>,
    isRLD: MutableState<Boolean>
) {
    Surface(
        modifier = Modifier
            .size(
                width = MaterialTheme.dimens.superAdminSettingScrSurfaceW,
                height = MaterialTheme.dimens.superAdminSettingScrSurfaceH
            )
            .padding(top = MaterialTheme.dimens.superAdminSettingScrSurfaceTopPad),
        color = OnTimeColors.PORT_GORE
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(id = R.string.Trust_or_Organization),
                    color = Color.White,
                    modifier = Modifier.padding(start = MaterialTheme.dimens.superAdminSettingScrColStartPad)
                )
                InputField(
                    modifier = Modifier.size(
                        width = MaterialTheme.dimens.superAdminSettingScrTxtFieldsW,
                        height = MaterialTheme.dimens.superAdminSettingScrTxtFieldsH
                    ),
                    valueState = trustState,
                    labelId = "",
                    enabled = false,
                    isSingleLine = true,
                    textStyle = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(id = R.string.Location_Do_not_add_semi_colon),
                    color = OnTimeColors.White,
                    modifier = Modifier.padding(start = MaterialTheme.dimens.superAdminSettingScrColStartPad)
                )
                InputField(
                    modifier = Modifier.size(
                        width = MaterialTheme.dimens.superAdminSettingScrTxtFieldsW,
                        height = MaterialTheme.dimens.superAdminSettingScrTxtFieldsH
                    ),
                    valueState = locationState,
                    labelId = "",
                    enabled = false,
                    isSingleLine = true,
                    textStyle = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(id = R.string.Postcode),
                    color = OnTimeColors.White,
                    modifier = Modifier.padding(start = MaterialTheme.dimens.superAdminSettingScrColStartPad)
                )
                InputField(
                    modifier = Modifier.size(
                        width = MaterialTheme.dimens.superAdminSettingScrTxtFieldsW,
                        height = MaterialTheme.dimens.superAdminSettingScrTxtFieldsH
                    ),
                    valueState = postCodeState,
                    labelId = "",
                    enabled = false,
                    isSingleLine = true,
                    textStyle = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerW10))
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(id = R.string.Unique_Identifier_only),
                    color = OnTimeColors.White,
                    modifier = Modifier.padding(start = MaterialTheme.dimens.superAdminSettingScrColStartPad)
                )
                InputField(
                    modifier = Modifier.size(
                        width = MaterialTheme.dimens.superAdminSettingScrTxtFieldsW,
                        height = MaterialTheme.dimens.superAdminSettingScrTxtFieldsH
                    ),
                    valueState = uniqueIdentifierState,
                    labelId = "",
                    enabled = false,
                    isSingleLine = true,
                    textStyle = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(id = R.string.LatLng),
                    color = OnTimeColors.White,
                    modifier = Modifier.padding(start = MaterialTheme.dimens.superAdminSettingScrColStartPad)
                )
                InputField(
                    modifier = Modifier.size(
                        width = MaterialTheme.dimens.superAdminSettingScrTxtFieldsW,
                        height = MaterialTheme.dimens.superAdminSettingScrTxtFieldsH
                    ),
                    valueState = latLngState,
                    labelId = "",
                    enabled = false,
                    isSingleLine = true,
                    textStyle = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Site Name",
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = MaterialTheme.dimens.superAdminSettingScrColStartPad)
                        .alpha(if (isRLD.value) 0f else 1f)
                )
                InputField(
                    modifier = Modifier
                        .size(
                            width = MaterialTheme.dimens.superAdminSettingScrTxtFieldsW,
                            height = MaterialTheme.dimens.superAdminSettingScrTxtFieldsH
                        )
                        .alpha(if (isRLD.value) 0f else 1f),
                    valueState = siteNameState,
                    labelId = "",
                    enabled = false,
                    isSingleLine = true,
                    textStyle = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}