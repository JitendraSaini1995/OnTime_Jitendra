package com.allocate.ontime.presentation_logic.screens.super_admin

import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.allocate.ontime.MainActivity
import com.allocate.ontime.R
import com.allocate.ontime.business_logic.data.room.entities.EmployeePacket
import com.allocate.ontime.business_logic.utils.OnTimeColors
import com.allocate.ontime.business_logic.viewmodel.super_admin.FobRegisterViewModel
import com.allocate.ontime.presentation_logic.model.NfcModel
import com.allocate.ontime.presentation_logic.navigation.SuperAdminScreenRoot
import com.allocate.ontime.presentation_logic.theme.dimens
import com.allocate.ontime.presentation_logic.widgets.InputField
import com.google.gson.Gson
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun FobRegisterScreen(
    backToSuperAdminScreen: (SuperAdminScreenRoot) -> Unit,
    fobRegisterViewModel: FobRegisterViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val searchResults = fobRegisterViewModel.searchResults.collectAsState()
    val searchQuery = remember { mutableStateOf("") }

    val locationRadiusState = remember { mutableStateOf("") }

    val isVisible = remember { mutableStateOf(true) }

    val isSwitchOn = remember { mutableStateOf(false) }

    val empId = remember {
        mutableStateOf("0")
    }

    val fobVersion = remember {
        mutableIntStateOf(0)
    }

    val hasNoUserInteractionFobRegistrationScreen =
        fobRegisterViewModel.navigationFlow.collectAsState()

    if (hasNoUserInteractionFobRegistrationScreen.value) {
        backToSuperAdminScreen(SuperAdminScreenRoot.SuperAdminScreen)
        fobRegisterViewModel.resetAutoBack()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        fobRegisterViewModel.startInteraction()
                    }
                )
            },
        color = OnTimeColors.TORY_BLUE
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                top = MaterialTheme.dimens.s3,
                bottom = MaterialTheme.dimens.s3
            )
        ) {
            Text(
                text = stringResource(id = R.string.FOB_REGISTER),
                style = MaterialTheme.typography.headlineMedium,
                color = OnTimeColors.GREEN_HAZE,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(0.2f))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(searchQuery) { newQuery ->
                    searchQuery.value = newQuery.lowercase()
                    fobRegisterViewModel.getEmployeeData()
                }
                if (searchQuery.value.isNotEmpty()) {
                    EmployeeList(searchResults.value, searchQuery.value) { employee ->
                        searchQuery.value =
                            "${employee.iD} ${employee.firstName} ${employee.lastName}"
                        empId.value = employee.iD
                        fobVersion.intValue = employee.fobVersion
                    }
//                    WriteNfcTag(searchQuery.value)
                }

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.s1))
                AnimatedVisibility(
                    visible = if (!isSwitchOn.value) {
                        isVisible.value
                    } else {
                        !isVisible.value
                    }, enter = fadeIn(), exit = fadeOut()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.width(MaterialTheme.dimens.fobRegScrSetLocRowW)
                    ) {
                        Text(
                            text = stringResource(id = R.string.Set_location_Radius),
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall
                        )
                        OutlinedTextField(
                            value = locationRadiusState.value,
                            onValueChange = { locationRadiusState.value = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Default
                            ),
                            singleLine = true,
                            modifier = Modifier.size(
                                width = MaterialTheme.dimens.fobRegScrSetLocTxtFieldWidth,
                                height = MaterialTheme.dimens.fobRegScrSetLocTxtFieldH
                            ),
                            textStyle = MaterialTheme.typography.titleSmall,
                            colors = run {
                                val containerColor = OnTimeColors.LightPink
                                TextFieldDefaults.colors(
                                    unfocusedTextColor = OnTimeColors.LightGray,
                                    focusedContainerColor = containerColor,
                                    unfocusedContainerColor = containerColor,
                                    disabledContainerColor = containerColor,
                                    cursorColor = OnTimeColors.Cyan,
                                    focusedIndicatorColor = OnTimeColors.White,
                                    focusedLabelColor = OnTimeColors.White,
                                    focusedSupportingTextColor = OnTimeColors.White,
                                )
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.Miles),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.s1))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .width(MaterialTheme.dimens.fobRegScrSetLocRowW)
                ) {
                    Text(
                        text = stringResource(id = R.string.All_Locations),
                        color = OnTimeColors.White,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Switch(
                        checked = isSwitchOn.value,
                        onCheckedChange = {
                            isSwitchOn.value = it
                        },
                        colors = SwitchDefaults.colors(
                            uncheckedThumbColor = OnTimeColors.White,
                            uncheckedTrackColor = OnTimeColors.LightGray,
                            checkedThumbColor = OnTimeColors.Cyan,
                            checkedTrackColor = OnTimeColors.White
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Button(
                onClick = {
                    if (!fobRegisterViewModel.isAllLocation.value && locationRadiusState.value.isEmpty() || locationRadiusState.value == "0") {
                        Toast.makeText(context, R.string.enter_radius, Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(context, MainActivity::class.java).apply {
                            val nfcModel = NfcModel()
                            nfcModel.employeeId = empId.value
                            nfcModel.nfcVersion = fobVersion.intValue + 1
                            fobRegisterViewModel._fobVersion.value = nfcModel.nfcVersion
                            val nfcText = Gson().toJson(nfcModel)
                            putExtra("employeeData", nfcText)
                        }
                        backToSuperAdminScreen(SuperAdminScreenRoot.FobImageScreen)
                        context.startActivity(intent)
                    }
                },
                shape = RoundedCornerShape(MaterialTheme.dimens.fobRegScrBtnCornerSz),
                colors = ButtonDefaults.buttonColors(
                    containerColor = OnTimeColors.GREEN_HAZE, contentColor = OnTimeColors.White
                )
            ) {
                Text(text = stringResource(id = R.string.Ready))
            }
            Spacer(modifier = Modifier.weight(MaterialTheme.dimens.fobRegScrSpacerWeight))
            Button(
                onClick = {
                    backToSuperAdminScreen(SuperAdminScreenRoot.SuperAdminScreen)
                },
                shape = RoundedCornerShape(MaterialTheme.dimens.fobRegScrBtnCornerSz),
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
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: MutableState<String>,
    onSearchQueryChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = searchQuery.value,
        onValueChange = onSearchQueryChange,
        label = { Text(stringResource(id = R.string.Search_Employee)) },
        trailingIcon = {
            if (searchQuery.value.isNotEmpty()) {
                IconButton(onClick = { searchQuery.value = "" }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }
        },
        singleLine = true,
        modifier = Modifier
            .size(
                width = MaterialTheme.dimens.fobRegScrSearchTxtFieldW,
                height = MaterialTheme.dimens.fobRegScrSearchTxtFieldH
            )
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        textStyle = TextStyle(fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground),
        colors = run {
            val containerColor = Color(0xFFD6B6C1)
            TextFieldDefaults.colors(
                unfocusedTextColor = Color.LightGray,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                cursorColor = Color.Cyan,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
                focusedSupportingTextColor = Color.White,
            )
        },
        shape = RoundedCornerShape(5.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        )
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun EmployeeList(
    employees: List<EmployeePacket>,
    searchQuery: String,
    onEmployeeClick: (EmployeePacket) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val filteredEmployees = employees.filter { employee ->
        searchQuery.lowercase(Locale.ROOT).run {
            employee.firstName?.lowercase(Locale.ROOT)?.startsWith(this) == true ||
                    employee.lastName?.lowercase(Locale.ROOT)?.startsWith(this) == true ||
                    (employee.firstName + " " + employee.lastName).lowercase(Locale.ROOT)
                        .startsWith(this) ||
                    (employee.iD + " " + employee.firstName + " " + employee.lastName).lowercase(
                        Locale.ROOT
                    )
                        .startsWith(this)
        }
    }
    LazyColumn(content = {
        items(filteredEmployees) { employee ->
            EmployeeItem(employee, onEmployeeClick)
            keyboardController?.hide()
        }
    })
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun EmployeeItem(employee: EmployeePacket, onEmployeeClick: (EmployeePacket) -> Unit) {
    Surface(
        modifier = Modifier
            .size(
                width = 375.dp,
                height = 30.dp
            )
            .padding(end = MaterialTheme.dimens.fobRegScrSearchTxtFieldEndPad)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onEmployeeClick(employee) }
                )
            },
    ) {
        Text(
            text = "${employee.iD} ${employee.firstName} ${employee.lastName}",
        )

    }
}

//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//@Composable
//fun WriteNfcTag(employee: String) {
//    val context = LocalContext.current
//    Log.d("Nfc","WriteNfcTag run successfully")
//
//    val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
//    val nfcPendingIntent = PendingIntent.getActivity(
//        context, 0,
//        Intent(context, context.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
//        } else {
//            PendingIntent.FLAG_UPDATE_CURRENT
//        }
//    )
//
//    DisposableEffect(Unit) {
//        nfcAdapter?.enableForegroundDispatch(
//            context as Activity, nfcPendingIntent,
//            arrayOf(IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)), null
//        )
//        Log.d("Nfc","disposeEffect run successfully")
//
//        onDispose {
//            nfcAdapter?.disableForegroundDispatch(context as Activity)
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        if (nfcAdapter == null || !nfcAdapter.isEnabled) {
//            Toast.makeText(context, "NFC is not available or enabled", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    val writeTag: (Tag) -> Unit = { tag ->
//        val nfcMessage = NdefMessage(
//            arrayOf(NdefRecord.createTextRecord("en", employee))
//        )
//
//        try {
//            val ndef = Ndef.get(tag)
//            if (ndef != null) {
//                ndef.connect()
//                if (ndef.isWritable) {
//                    ndef.writeNdefMessage(nfcMessage)
//                    Log.d("Nfc","nfc tag written successfully")
//                    Toast.makeText(context, "NFC Tag written successfully!", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "NFC tag is not writable", Toast.LENGTH_SHORT).show()
//                }
//                ndef.close()
//            } else {
//                Toast.makeText(context, "NFC tag does not support NDEF", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: Exception) {
//            Toast.makeText(context, "Failed to write NFC Tag: ${e.message}", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    val nfcDispatcher = rememberUpdatedState(newValue = writeTag)
//    DisposableEffect(Unit) {
//        val receiver = object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent?) {
//                val tag = intent?.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
//                tag?.let { nfcDispatcher.value(it) }
//            }
//        }
//
//        val intentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
//        context.registerReceiver(receiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
//
//        onDispose {
//            context.unregisterReceiver(receiver)
//        }
//    }
//}



