package com.example.jettip.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val IconButtonSizeModifier=Modifier.size(40.dp)
@Composable
fun RoundCardButton(
    modifier: Modifier=Modifier,
    elevation:Dp,
    tint:Color= MaterialTheme.colorScheme.background,
    imageVector:ImageVector,
    onClick : () -> Unit,
){

    Card(
        modifier=modifier.padding(4.dp).clickable {
            onClick.invoke()
        }.then(IconButtonSizeModifier),
        shape = CircleShape,
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(elevation),
    ) {
        Box(modifier=Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
            Icon(
                imageVector = imageVector,
                contentDescription = "plus or minus",
                tint = tint,
            )
        }
    }
}