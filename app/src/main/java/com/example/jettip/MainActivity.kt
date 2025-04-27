package com.example.jettip

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettip.components.InputField
import com.example.jettip.ui.theme.JetTipTheme
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.jettip.ui.theme.Purple40
import com.example.jettip.widgets.RoundCardButton


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppThemeSetUp{
                BillForm {  }
            }
        }
    }
}

@Composable
 fun MyAppThemeSetUp(content:@Composable () -> Unit) {
     JetTipTheme {
         Surface(color = Color.White , modifier = Modifier.padding(top = 10.dp, start = 5.dp, end = 5.dp)) {
             content()
         }
     }

}

//@Preview
@Composable
fun TopHeader(totalValue:Double=134.0){

        Surface (modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp)))
            , color = Color(0xFFE9D7F7)){
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text="Total Per Person", fontSize = 20.sp, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text="$${"%.2f".format(totalValue)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )

            }
        }

}


@Composable
fun BillForm(onValChange: (String) -> Unit) {
    val totalBillValue = remember {
        mutableStateOf("")
    }
    val validate = remember(totalBillValue.value) {
        totalBillValue.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val splitCount = remember {
        mutableIntStateOf(1)
    }

    val sliderPositionState = remember {
        androidx.compose.runtime.mutableFloatStateOf(0f)
    }
    val tipPercentageValue = (sliderPositionState.floatValue * 100).toInt()

    val splitRange = IntRange(start = 1, endInclusive = 10)

    val tipValue= remember(sliderPositionState.floatValue, totalBillValue.value) {
        derivedStateOf {
            getValueFromPercentage(tipPercentageValue.toDouble(), totalBillValue.value.toDoubleOrNull() ?: 0.0)
        }
    }

    val totalPerPersonAmount by remember(sliderPositionState.floatValue, totalBillValue.value, splitCount.intValue) {
        derivedStateOf {
            calculateTotalForPerPerson(splitCount.intValue, tipValue.value, totalBillValue.value.toDoubleOrNull() ?: 0.0)
        }
    }


    Column {
        TopHeader(totalPerPersonAmount)
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 2.dp, color = Color.LightGray, RoundedCornerShape(12.dp))
                .padding(10.dp),
            color = Color(0xFFFFFFFF),
            shape = RoundedCornerShape(corner = CornerSize(12.dp))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                InputField(valueState = totalBillValue,
                    labelId = "Enter Bill",
                    sinlgeLine = true,
                    enabled = true,
                    onAction = KeyboardActions {
                        if (!validate) return@KeyboardActions
                        onValChange(totalBillValue.value)
                        keyboardController?.hide()
                    }
                )
                if (validate) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(60.dp)
                            .padding(start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Split", fontSize = 15.sp)
                        Spacer(modifier = Modifier.size(150.dp))
                        Row(
                            modifier = Modifier.padding(3.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RoundCardButton(
                                elevation = 3.dp,
                                imageVector = Icons.Default.Remove,
                                onClick = {
                                    Log.d("TAG", "BillForm:Remove ")
                                    splitCount.intValue =
                                        if (splitCount.intValue > 1) splitCount.intValue - 1 else 1
                                })
                            Text(
                                text = "${splitCount.intValue}",
                                fontSize = 15.sp,
                                modifier = Modifier.padding(start = 9.dp, end = 9.dp)
                            )
                            RoundCardButton(
                                elevation = 3.dp,
                                imageVector = Icons.Default.Add,
                                onClick = {
                                    Log.d("TAG", "BillForm:add ")
                                    if (splitCount.intValue < splitRange.last) {
                                        splitCount.value += 1
                                    }
                                })
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.padding(horizontal = 10.dp).height(30.dp)) {
                        Text(text = "Tip", fontSize = 15.sp)
                        Spacer(modifier = Modifier.size(250.dp))
                        Text(text = "${tipValue.value}", fontSize = 15.sp)
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "$tipPercentageValue %", fontSize = 15.sp)
                    }
                    Slider(
                        value = sliderPositionState.floatValue, onValueChange = {
                            sliderPositionState.floatValue = it
                        },
                        modifier = Modifier.padding(16.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = Purple40, // Change the thumb color
                            activeTrackColor = Purple40, // Change the active track color
                            inactiveTrackColor = Color.Gray, // Change the inactive track color
                            activeTickColor = Color.Yellow, // Change active tick marks
                            inactiveTickColor = Color.Blue // Change inactive tick marks
                        )
                    )

                } else {
                    Box {}
                }
            }
        }
    }
}

fun calculateTotalForPerPerson(splitCount: Int, percentageVal: Double, totalvalue: Double): Double {
    return (totalvalue+percentageVal).div(splitCount)
}

fun getValueFromPercentage(percentage: Double, total: Double): Double {
    return (percentage / 100) * total
}
@Preview
@Composable
fun MainContentBox(){
    BillForm { billAmount->
        Log.d("TAG", "MainContentBox: $billAmount")
    }
}



