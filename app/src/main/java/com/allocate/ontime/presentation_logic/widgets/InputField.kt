package com.allocate.ontime.presentation_logic.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allocate.ontime.R

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    isTrailingIcon: Boolean = false,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean = true,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    colors: TextFieldColors = run {
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
    shape: Shape = RoundedCornerShape(5.dp),
    textStyle: TextStyle,
    onValueChange: (String) -> Unit = {},
    trailingIcon: @Composable () -> Unit = {}

) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = {
            Text(text = labelId, style = textStyle)
        },
        trailingIcon = {
            if (isTrailingIcon) {
                Image(
                    painter = painterResource(id = R.drawable.cross),
                    contentDescription = "cross_icon",
                    modifier = Modifier.aspectRatio(0.5f)
                )
            }
        },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        colors = colors,
        shape = shape,
    )
}