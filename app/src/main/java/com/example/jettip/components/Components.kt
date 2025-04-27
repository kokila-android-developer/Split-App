package com.example.jettip.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier=Modifier,
    valueState:MutableState<String>,
    enabled:Boolean,
    sinlgeLine:Boolean,
    labelId:String,
    keyboardType: KeyboardType=KeyboardType.Number,
    imeAction: ImeAction=ImeAction.Done,
    onAction:KeyboardActions=KeyboardActions.Default){
    OutlinedTextField(value = valueState.value,
        onValueChange = {valueState.value=it},
        label = { Text(text = labelId) },
        leadingIcon = { Icon(imageVector = Icons.Rounded.AttachMoney,
            contentDescription = "money",modifier=Modifier.size(15.dp)) },
       textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.background),
        singleLine = sinlgeLine,
        enabled=enabled,
        keyboardOptions = KeyboardOptions(keyboardType=keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        modifier = modifier.padding(10.dp).fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Blue,  // Color when active
            unfocusedBorderColor = Color.Gray, // Color when inactive
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Gray,
            focusedLeadingIconColor = Color.Blue,
            unfocusedLeadingIconColor = Color.Gray,
        )
        )
}