package com.example.tipcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    TipCalc()
                }
            }
        }
    }
}
@Composable
fun TipCalc(){
    Column(
        modifier = Modifier
            .padding(top = 3.dp)
            .fillMaxSize()
//        .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
//        Image(painter = painterResource(id = R.drawable.phone_wallpaper), contentDescription = null)

        ) {
        Text(text = stringResource(R.string.Calc_tip,":"),
            modifier = Modifier
                .padding(vertical = 42.dp, horizontal = 12.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 12.dp, bottom = 23.dp),
            color = Color.Black,
            fontSize = 34.sp,
            fontStyle = FontStyle.Italic,
            textDecoration = TextDecoration.Underline
        )
        var amounts by remember {
            mutableStateOf("")
        }
        val amountInput = amounts.toDoubleOrNull() ?: 0.0

        var tipInput by remember {
            mutableStateOf("")
        }
        var roundUp by remember {
            mutableStateOf(false)
        }
        val tippercent = tipInput.toDoubleOrNull()?:0.0
        val tip = CalcTips(amountInput,tippercent,roundUp)

        EditTexts(label = R.string.Bill_Amount,value = amounts, onValueChange = { amounts = it}, modifier = Modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth(), keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ))
        EditTexts(label = R.string.tip_percent, value = tipInput, onValueChange = {tipInput=it}, modifier = Modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth(), keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ))
        Spacer(modifier = Modifier.height(4.dp))

        RoundUpTip(roundUp = roundUp, onRoundupChange = {roundUp=it},modifier = Modifier.padding(35.dp))
        Text(text = "Your Tip is : $tip",
            modifier = Modifier
                .padding(top = 1.dp)
                .padding(horizontal = 23.dp, vertical = 23.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.displaySmall,
            color = Color.Black


        )
               }


    }

@Composable
fun CalcTips(amount:Double,tipPercent:Double = 15.0,roundUp: Boolean):String{

    var tipamt = tipPercent/100*amount
    if(roundUp){
        tipamt = kotlin.math.ceil(tipamt)
    }
    return NumberFormat.getCurrencyInstance().format(tipamt)

}
//@SuppressLint("UnrememberedMutableState")
@Composable
fun EditTexts(@StringRes label :Int,value: String,onValueChange: (String)->Unit,modifier: Modifier=Modifier, keyboardOptions:KeyboardOptions){


    TextField(value = value,
        label = { Text(stringResource(label),
            modifier = Modifier.padding(start = 12.dp))
                },
        singleLine = true,
        onValueChange = onValueChange,
        modifier=modifier,
        keyboardOptions = keyboardOptions,
//        leadingIcon = Icon(painterResource(id = R.drawable.phone_wallpaper), contentDescription =null )

    )
}
@Composable
fun RoundUpTip(roundUp:Boolean,onRoundupChange:(Boolean)->Unit,modifier: Modifier){
    Row(modifier= modifier
        .fillMaxWidth()
        .wrapContentWidth(Alignment.Start)
//        .padding(end = 34.dp)
        ) {
//        Text(text = "hai--",
//            color = Color.Black)
        Text(text = "Round Tip ?", color = Color.Black,
            modifier = modifier.padding(end = 93.dp, top = 11.dp,
                ), fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            fontFamily =  FontFamily.Serif,

                )

        Switch(checked = roundUp, onCheckedChange = onRoundupChange,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End))
    }
}

@Composable
@Preview()
fun prevs(){
    TipCalculatorTheme {
        TipCalc()
    }
}
