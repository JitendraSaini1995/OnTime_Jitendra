package com.allocate.ontime.presentation_logic.screens.admin

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.allocate.ontime.R
import com.allocate.ontime.business_logic.utils.OnTimeColors
import com.allocate.ontime.business_logic.viewmodel.admin.AdminViewModel
import com.allocate.ontime.presentation_logic.navigation.HomeScreenRoot
import com.allocate.ontime.presentation_logic.theme.dimens
import com.allocate.ontime.presentation_logic.screens.login.PinEntryDialog

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AdminScreen(
    backToHome: (HomeScreenRoot) -> Unit,
    adminViewModel: AdminViewModel = hiltViewModel(),
) {
    val TAG = "AdminScreen"
    Log.d("jitu","Admin Screen")
    var isDialogVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (isDialogVisible) {
        PinEntryDialog(onDismiss = {
            isDialogVisible = false
        }, onPinEntered = { pin ->
            Toast.makeText(context, "Entered PIN: $pin", Toast.LENGTH_SHORT).show()
        })
    }

    val hasNoUserInteractionAdminScreen = adminViewModel.navigationFlows.collectAsState()

    if (hasNoUserInteractionAdminScreen.value) {
        backToHome(HomeScreenRoot.HomeScreen)
        adminViewModel.resetAutoBack()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        adminViewModel.startInteraction()
                        Log.d(TAG, "after OnTap: ${hasNoUserInteractionAdminScreen.value}")
                    }
                )
            },
        color = OnTimeColors.TORY_BLUE
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.homeScrBtnCornerSz,
                        end = MaterialTheme.dimens.adminScrTopRowStartPad,
                        top = MaterialTheme.dimens.adminScrTopRowEndPad
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = true,
                        onClick = { /*TODO*/ },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.White),
                    )

                    Text(
                        text = "2:23 PM",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column {
                    Text(
                        text = stringResource(id = R.string.WELCOME_TO_ADMIN_PAGE),
                        style = MaterialTheme.typography.headlineMedium,
                        color = OnTimeColors.GREEN_HAZE,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH5))
                    Text(
                        text = stringResource(id = R.string.Administrator_to_Log_In),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                    Text(
                        text = stringResource(id = R.string.via_the_Fingerprint_Reader),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.fingerprint_rld),
                        contentDescription = stringResource(id = R.string.place_finger_logo),
                        colorFilter = ColorFilter.tint(color = Color.White),
                        modifier = Modifier.size(MaterialTheme.dimens.m3)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH2))
                    Text(
                        text = stringResource(id = R.string.Place_Finger),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.fillMaxHeight(MaterialTheme.dimens.adminScrSpacerFillMaxH))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = MaterialTheme.dimens.adminScrBottomRowStartPad),
                horizontalArrangement = Arrangement.End


            ) {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = MaterialTheme.dimens.adminScrBottomRowBottomPad)
                ) {


                    Button(
                        onClick = {
                            isDialogVisible = true
                        },
                        shape = RoundedCornerShape(MaterialTheme.dimens.adminScrBtnCornerSz),
                        colors = ButtonDefaults.buttonColors(containerColor = OnTimeColors.GREEN_HAZE),
                        contentPadding = PaddingValues(
                            horizontal = MaterialTheme.dimens.adminScrContentPadH,
                            vertical = MaterialTheme.dimens.adminScrContentPadV
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.ENTER_PIN),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.s3))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = {
                                backToHome(HomeScreenRoot.HomeScreen)
                            },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OnTimeColors.PORT_GORE
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.Click_here_to_home_page),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerW15))
                        Button(
                            onClick = {
                                Log.d(TAG, "after OnTap: ${hasNoUserInteractionAdminScreen.value}")
                            },
                            shape = RoundedCornerShape(MaterialTheme.dimens.adminScrBtnCornerSz),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OnTimeColors.PORT_GORE
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.View_Employee_Online),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.adminScrSpacerW))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_nfc),
                            contentDescription = stringResource(id = R.string.fob_icon),
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(color = Color.White),
                            modifier = Modifier.size(MaterialTheme.dimens.m3)
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH2))
                        Text(
                            text = stringResource(id = R.string.FOB),
                            style = MaterialTheme.typography.headlineSmall,
                            color = OnTimeColors.White,
                        )
                    }
                    Spacer(modifier = Modifier.weight(MaterialTheme.dimens.adminScrSpacerWeight))
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(bottom = MaterialTheme.dimens.adminScrBottomRowBottomPad)
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_info),
                            color = OnTimeColors.White,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = stringResource(id = R.string.Unique_Identifier),
                            color = OnTimeColors.White,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }

}





