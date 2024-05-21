package com.allocate.ontime.presentation_logic.screens.super_admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.allocate.ontime.R
import com.allocate.ontime.business_logic.utils.OnTimeColors
import com.allocate.ontime.presentation_logic.navigation.SuperAdminScreenRoot

@Composable
fun FobImageScreen(
    superAdminScreenRoot: (SuperAdminScreenRoot) -> Unit,
) {
    Surface(
        modifier = Modifier
            .height(300.dp)
            .width(400.dp),
        color = OnTimeColors.White,
        border = BorderStroke(width = 1.dp, color = OnTimeColors.Black)
    ){
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.Ready_To_Connect),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = R.drawable.nfc),
                contentDescription = stringResource(id = R.string.fob_icon),
                contentScale = ContentScale.Fit,
            )
            Text(
                text = stringResource(id = R.string.Touch_And_Hold_NFC_Tag_Near_Device),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = {
                    superAdminScreenRoot(SuperAdminScreenRoot.FobRegisterScreen)
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = OnTimeColors.DarkGray
                )
            ) {
                Text(
                    text = stringResource(id = R.string.CANCEL),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}





