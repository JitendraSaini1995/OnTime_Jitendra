package com.allocate.ontime.presentation_logic.screens.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.allocate.ontime.R
import com.allocate.ontime.business_logic.data.shared_preferences.SecureSharedPrefs
import com.allocate.ontime.business_logic.utils.Constants
import com.allocate.ontime.business_logic.utils.OnTimeColors
import com.allocate.ontime.presentation_logic.navigation.HomeScreenRoot
import com.allocate.ontime.presentation_logic.theme.dimens
import com.allocate.ontime.presentation_logic.screens.login.PinEntryDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun HomeScreen(
    homeScreenRoot: (HomeScreenRoot) -> Unit
) {
    fun getCurrentTime(): String {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        return dateFormat.format(currentTime)
    }

    var isDialogVisible by remember { mutableStateOf(false) }
    val currentTime by remember { mutableStateOf(getCurrentTime()) }
    val context = LocalContext.current
    if (isDialogVisible) {
        PinEntryDialog(onDismiss = {
            isDialogVisible = false
        }, onPinEntered = { pin ->
            Toast.makeText(context, "Entered PIN: $pin", Toast.LENGTH_SHORT).show()
        })
    }

//    val userName = SecureSharedPrefs(context).getData(Constants.USER_NAME, "")
//    val password = SecureSharedPrefs(context).getData(Constants.PASSWORD, "")
//    val asApiUrl = SecureSharedPrefs(context).getData(Constants.AS_API_URL, "")
//    val deviceSettingData = SecureSharedPrefs(context).getData(Constants.DEVICE_SETTING_DATA, "")


    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimens.s1),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = MaterialTheme.dimens.s3, top = MaterialTheme.dimens.m1),
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
                        colors = RadioButtonDefaults.colors(selectedColor = OnTimeColors.PORT_GORE)
                    )
                    Text(
                        text = currentTime,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnTimeColors.PORT_GORE
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ontime_icon),
                    contentDescription =
                    stringResource(id = R.string.rld_logo),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth(MaterialTheme.dimens.homeScrRldLogoMaxWFract)
                        .fillMaxHeight(MaterialTheme.dimens.homeScrRldLogoMaxHFract)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.fingerprint_rld),
                        contentDescription =
                        stringResource(id = R.string.place_finger_logo),
                        colorFilter = ColorFilter.tint(color = OnTimeColors.PORT_GORE),
                        modifier = Modifier.size(MaterialTheme.dimens.m3)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH2))
                    Text(
                        text = stringResource(id = R.string.Place_Finger),
                        style = MaterialTheme.typography.headlineSmall,
                        color = OnTimeColors.PORT_GORE
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.homeScrSpacerH))
            Image(
                painter = painterResource(id = R.drawable.ontime_logo),
                contentDescription = stringResource(id = R.string.onTime_logo),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(MaterialTheme.dimens.homeScrOnTimeLogoMaxWFract)
                    .fillMaxHeight(MaterialTheme.dimens.homeScrOnTimeLogoMaxHFract)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = MaterialTheme.dimens.m1),
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = MaterialTheme.dimens.s3)
                ) {
                    Button(
                        onClick = {
                            isDialogVisible = true
                        },
                        shape = RoundedCornerShape(MaterialTheme.dimens.homeScrEnterPinBtnRoundedCornerSz),
                        colors = ButtonDefaults.buttonColors(containerColor = OnTimeColors.GREEN_HAZE),
                        contentPadding = PaddingValues(
                            horizontal = MaterialTheme.dimens.homeScrContentPadH,
                            vertical = MaterialTheme.dimens.homeScrContentPadV
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.ENTER_PIN),
                            style = MaterialTheme.typography.titleLarge
                        )

                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.s3))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = {
                                Log.d("jitu","Button Clicked")
                                val startTime  =  mutableLongStateOf(System.currentTimeMillis())
                                Log.d("jitu","start time : $startTime")
                                homeScreenRoot(HomeScreenRoot.AdminScreen)
                            },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OnTimeColors.PORT_GORE
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.Administrator_Access),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerW15))
                        Button(
                            onClick = {
                                homeScreenRoot(HomeScreenRoot.SuperAdminScreen)
                            },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OnTimeColors.PORT_GORE
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.View_Employee_Online),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.homeScrSpacerW))
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
                            colorFilter = ColorFilter.tint(color = OnTimeColors.PORT_GORE),
                            modifier = Modifier.size(MaterialTheme.dimens.m3)
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerH2))
                        Text(
                            text = stringResource(id = R.string.FOB),
                            style = MaterialTheme.typography.headlineSmall,
                            color = OnTimeColors.PORT_GORE,
                        )
                    }
                    Spacer(modifier = Modifier.weight(MaterialTheme.dimens.homeScrSpacerWeight))
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(bottom = MaterialTheme.dimens.homeScrBottomRowBottomPad)
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_info),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = stringResource(id = R.string.Unique_Identifier),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}


