package com.allocate.ontime.presentation_logic.screens.super_admin

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.allocate.ontime.R
import com.allocate.ontime.business_logic.utils.OnTimeColors
import com.allocate.ontime.presentation_logic.navigation.SuperAdminScreenRoot
import com.allocate.ontime.presentation_logic.theme.dimens
import com.allocate.ontime.presentation_logic.widgets.InputField

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun VisitorRegistrationScreen(backToSuperAdminScreen: (SuperAdminScreenRoot) -> Unit) {

    val searchVisitor = remember {
        mutableStateOf("")
    }
    val firstName = remember {
        mutableStateOf("")
    }
    val lastName = remember {
        mutableStateOf("")
    }
    val companyName = remember {
        mutableStateOf("")
    }
    val visitingPerson = remember {
        mutableStateOf("")
    }
    val phoneNumber = remember {
        mutableStateOf("")
    }
    val vehicleRegistration = remember {
        mutableStateOf("")
    }
    val reason = remember {
        mutableStateOf("")
    }
    val searchState = remember {
        mutableStateOf("")
    }

    val checkBoxState = remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = OnTimeColors.TORY_BLUE
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
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
                                height = MaterialTheme.dimens.visitorRegScrUserRegSurfaceH
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
                                valueState = searchVisitor,
                                labelId = stringResource(id = R.string.Search_Visitor),
                                isSingleLine = true,
                                textStyle = MaterialTheme.typography.titleMedium
                            )
                            Row {
                                InputField(
                                    modifier = Modifier
                                        .size(
                                            width = MaterialTheme.dimens.visitorRegScrInputFieldWidth,
                                            height = MaterialTheme.dimens.userInTxtFieldH
                                        ),
                                    valueState = firstName,
                                    labelId = stringResource(id = R.string.First_Name),
                                    isSingleLine = true,
                                    textStyle = MaterialTheme.typography.titleMedium
                                )
                                InputField(
                                    modifier = Modifier
                                        .size(
                                            width = MaterialTheme.dimens.visitorRegScrInputFieldWidth,
                                            height = MaterialTheme.dimens.userInTxtFieldH
                                        ),
                                    valueState = lastName,
                                    labelId = stringResource(id = R.string.Last_Name),
                                    isSingleLine = true,
                                    textStyle = MaterialTheme.typography.titleMedium
                                )
                            }
                            Row {
                                InputField(
                                    modifier = Modifier
                                        .size(
                                            width = MaterialTheme.dimens.visitorRegScrInputFieldWidth,
                                            height = MaterialTheme.dimens.userInTxtFieldH
                                        ),
                                    valueState = companyName,
                                    labelId = stringResource(id = R.string.Company_name),
                                    isSingleLine = true,
                                    textStyle = MaterialTheme.typography.titleMedium
                                )
                                InputField(
                                    modifier = Modifier
                                        .size(
                                            width = MaterialTheme.dimens.visitorRegScrInputFieldWidth,
                                            height = MaterialTheme.dimens.userInTxtFieldH
                                        ),
                                    valueState = visitingPerson,
                                    labelId = stringResource(id = R.string.Visiting_person),
                                    isSingleLine = true,
                                    textStyle = MaterialTheme.typography.titleMedium
                                )
                            }
                            InputField(
                                modifier = Modifier
                                    .size(
                                        width = MaterialTheme.dimens.userInTxtFieldW,
                                        height = MaterialTheme.dimens.userInTxtFieldH
                                    ),
                                valueState = phoneNumber,
                                labelId = stringResource(id = R.string.Phone_number),
                                isSingleLine = true,
                                textStyle = MaterialTheme.typography.titleMedium
                            )
                            InputField(
                                modifier = Modifier
                                    .size(
                                        width = MaterialTheme.dimens.userInTxtFieldW,
                                        height = MaterialTheme.dimens.userInTxtFieldH
                                    ),
                                valueState = vehicleRegistration,
                                labelId = stringResource(id = R.string.Vehicle_Registration),
                                isSingleLine = true,
                                textStyle = MaterialTheme.typography.titleMedium
                            )
                            InputField(
                                modifier = Modifier
                                    .size(
                                        width = MaterialTheme.dimens.userInTxtFieldW,
                                        height = MaterialTheme.dimens.visitorRegScrReasonInputFieldWidth
                                    ),
                                valueState = reason,
                                labelId = stringResource(id = R.string.Reason),
                                isSingleLine = false,
                                textStyle = MaterialTheme.typography.titleMedium
                            )

                        }
                    }
                }
                Surface(
                    modifier = Modifier
                        .size(
                            width = MaterialTheme.dimens.surfW,
                            height = MaterialTheme.dimens.visitorRegScrRightSurfaceH
                        )
                        .padding(top = MaterialTheme.dimens.visitorRegScrRightSurfaceTopPad),
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
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH10))
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.login),
                                contentDescription = stringResource(id = R.string.fingerprint_img),
                                modifier = Modifier.size(MaterialTheme.dimens.visitorRegScrImgSize),
                            )
                            Image(
                                painter = painterResource(id = R.drawable.logout),
                                contentDescription = stringResource(id = R.string.fingerprint_img),
                                modifier = Modifier.size(MaterialTheme.dimens.visitorRegScrImgSize),
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.Clock_in),
                                style = MaterialTheme.typography.headlineSmall,
                                color = OnTimeColors.GREEN_HAZE
                            )
                            Text(
                                text = stringResource(id = R.string.Clock_out),
                                style = MaterialTheme.typography.headlineSmall,
                                color = OnTimeColors.ALIZARIN_CRIMSON
                            )

                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerW20))
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(MaterialTheme.dimens.adminRegScrBtnCornerSz),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OnTimeColors.GREEN_HAZE,
                                contentColor = OnTimeColors.White
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            contentPadding = PaddingValues(MaterialTheme.dimens.visitorRegScrRegisterBtnContentPad)
                        ) {
                            Text(text = stringResource(id = R.string.Register_and_make_event))
                        }


                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .weight(MaterialTheme.dimens.visitorRegScrSpacerWeight)
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