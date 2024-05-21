package com.allocate.ontime.presentation_logic.screens.super_admin

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.allocate.ontime.R
import com.allocate.ontime.business_logic.utils.OnTimeColors
import com.allocate.ontime.business_logic.viewmodel.super_admin.AdminRegistrationViewModel
import com.allocate.ontime.presentation_logic.navigation.SuperAdminScreenRoot
import com.allocate.ontime.presentation_logic.theme.dimens
import com.allocate.ontime.presentation_logic.widgets.InputField

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AdminRegistrationScreen(
    backToSuperAdminScreen: (SuperAdminScreenRoot) -> Unit,
    adminRegistrationViewModel: AdminRegistrationViewModel = hiltViewModel(),
) {

    val searchState = remember {
        mutableStateOf("")
    }

    val checkBoxState = remember {
        mutableStateOf(false)
    }

    val hasNoUserInteractionAdminRegistrationScreen =
        adminRegistrationViewModel.navigationFlow.collectAsState()
    Log.d(
        "AutoBackNavigationManager",
        "adminRegistration: ${hasNoUserInteractionAdminRegistrationScreen.value}"
    )

    if (hasNoUserInteractionAdminRegistrationScreen.value) {
        backToSuperAdminScreen(SuperAdminScreenRoot.SuperAdminScreen)
        adminRegistrationViewModel.resetAutoBack()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        adminRegistrationViewModel.startInteraction()
                    }
                )

            },
        color = OnTimeColors.TORY_BLUE
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.adminRegScrColTopPad)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.ADMIN_PAGE),
                style = MaterialTheme.typography.headlineMedium,
                color = OnTimeColors.GREEN_HAZE,
                fontWeight = FontWeight.Bold
            )
            InputField(
                modifier = Modifier
                    .size(
                        width = MaterialTheme.dimens.adminRegScrSearchTxtFieldW,
                        height = MaterialTheme.dimens.adminRegScrSearchTxtFieldH
                    )
                    .align(alignment = Alignment.End)
                    .padding(end = MaterialTheme.dimens.adminRegScrSearchTxtFieldEndPad),
                valueState = searchState,
                labelId = stringResource(id = R.string.Search_Employee),
                isSingleLine = true,
                textStyle = MaterialTheme.typography.titleMedium,
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.dimens.spacerH10)
                    .weight(MaterialTheme.dimens.adminRegScrSpacerWtAboveRegSurf)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.adminRegScrSurfaceRowStartPad,
                        end = MaterialTheme.dimens.adminRegScrSurfaceRowEndPad
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        text = stringResource(id = R.string.New_or_Edit_User_Registration),
                        color = OnTimeColors.White
                    )
                    Surface(
                        modifier = Modifier
                            .size(
                                width = MaterialTheme.dimens.surfW,
                                height = MaterialTheme.dimens.surfH
                            )
                            .padding(top = MaterialTheme.dimens.adminRegScrSurfaceTopPad),
                        color = OnTimeColors.PORT_GORE
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            InputField(
                                modifier = Modifier
                                    .size(
                                        width = MaterialTheme.dimens.userInTxtFieldW,
                                        height = MaterialTheme.dimens.userInTxtFieldH
                                    ),
                                valueState = searchState,
                                labelId = stringResource(id = R.string.First_Name),
                                isSingleLine = true,
                                textStyle = MaterialTheme.typography.titleMedium
                            )
                            InputField(
                                modifier = Modifier
                                    .size(
                                        width = MaterialTheme.dimens.userInTxtFieldW,
                                        height = MaterialTheme.dimens.userInTxtFieldH
                                    ),
                                valueState = searchState,
                                labelId = stringResource(id = R.string.Last_Name),
                                isSingleLine = true,
                                textStyle = MaterialTheme.typography.titleMedium
                            )
                            InputField(
                                modifier = Modifier
                                    .size(
                                        width = MaterialTheme.dimens.userInTxtFieldW,
                                        height = MaterialTheme.dimens.userInTxtFieldH
                                    ),
                                valueState = searchState,
                                labelId = stringResource(id = R.string.Employee_Number),
                                isSingleLine = true,
                                textStyle = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        text = stringResource(id = R.string.Fingerprint_Registration),
                        color = OnTimeColors.White
                    )
                    Surface(
                        modifier = Modifier
                            .size(
                                width = MaterialTheme.dimens.surfW,
                                height = MaterialTheme.dimens.surfH
                            )
                            .padding(top = MaterialTheme.dimens.adminRegScrSurfaceTopPad),
                        color = OnTimeColors.PORT_GORE
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Row(
                                modifier = Modifier.padding(start = MaterialTheme.dimens.adminRegScrFingerPrintRowStartPad),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                InputField(
                                    modifier = Modifier
                                        .size(
                                            width = MaterialTheme.dimens.fingerprintInTxtFieldW,
                                            height = MaterialTheme.dimens.fingerprintInTxtFieldH
                                        ),
                                    isTrailingIcon = true,
                                    valueState = searchState,
                                    labelId = stringResource(id = R.string.Fingerprint_One),
                                    isSingleLine = true,
                                    textStyle = MaterialTheme.typography.titleMedium
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.fingerprint),
                                    contentDescription = stringResource(id = R.string.fingerprint_img),
                                    modifier = Modifier.aspectRatio(MaterialTheme.dimens.adminRegScrFingerprintImgAspectRatio),
                                )
                            }
                            Row(
                                modifier = Modifier.padding(start = MaterialTheme.dimens.adminRegScrFingerPrintRowStartPad),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                InputField(
                                    modifier = Modifier
                                        .size(
                                            width = MaterialTheme.dimens.fingerprintInTxtFieldW,
                                            height = MaterialTheme.dimens.fingerprintInTxtFieldH
                                        ),
                                    isTrailingIcon = true,
                                    valueState = searchState,
                                    labelId = stringResource(id = R.string.Fingerprint_Two),
                                    isSingleLine = true,
                                    textStyle = MaterialTheme.typography.titleMedium
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.fingerprint),
                                    contentDescription = stringResource(id = R.string.fingerprint_img),
                                    modifier = Modifier.aspectRatio(MaterialTheme.dimens.adminRegScrFingerprintImgAspectRatio),
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = MaterialTheme.dimens.adminRegScrFingerPrintRowStartPad)
                            ) {
                                Checkbox(
                                    checked = false,
                                    onCheckedChange = { checkBoxState.value },
                                    colors = CheckboxDefaults.colors(uncheckedColor = OnTimeColors.White)
                                )
                                Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerW5))
                                Text(
                                    text = stringResource(id = R.string.Skip_Fingerprint),
                                    color = OnTimeColors.White
                                )
                            }
                        }
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .weight(MaterialTheme.dimens.adminRegScrSpacerWtBelowRegSurf)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ontime_qr_code),
                    contentDescription = stringResource(id = R.string.qr_code_img),
                    modifier = Modifier.size(MaterialTheme.dimens.qrCodeLogoSz)
                )
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerW20))
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(MaterialTheme.dimens.adminRegScrBtnCornerSz),
                    colors = ButtonDefaults.buttonColors(containerColor = OnTimeColors.GREEN_HAZE),
                    modifier = Modifier.size(
                        width = MaterialTheme.dimens.nextBtnW,
                        height = MaterialTheme.dimens.nextBtnH
                    )
                ) {
                    Text(text = stringResource(id = R.string.NEXT))
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH10))
            Text(
                text = stringResource(id = R.string.Please_scan_QR_Code_for_instructional_video_on_Finger_Scan_registrations),
                color = OnTimeColors.White,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.dimens.adminRegScrTxtStartPad),
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.dimens.spacerH10)
                    .weight(MaterialTheme.dimens.adminRegScrSpacerWtBelowQrCode)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.dimens.adminScrBottomRowBottomPad),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        backToSuperAdminScreen(SuperAdminScreenRoot.SuperAdminScreen)
                    },
                    shape = RoundedCornerShape(MaterialTheme.dimens.adminRegScrBtnCornerSz),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OnTimeColors.GREEN_HAZE, contentColor = OnTimeColors.White
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.circleImgAndTextSpace),
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
                    shape = RoundedCornerShape(MaterialTheme.dimens.adminRegScrBtnCornerSz),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OnTimeColors.GREEN_HAZE, contentColor = OnTimeColors.White
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.circleImgAndTextSpace),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.circle_green),
                            contentDescription = stringResource(id = R.string.circle_green_img),
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(OnTimeColors.MANTIS),
                            modifier = Modifier.size(MaterialTheme.dimens.circleGrnImgSz)
                        )
                        Text(text = stringResource(id = R.string.View_Employee_Online))
                    }
                }
            }
        }
    }
}