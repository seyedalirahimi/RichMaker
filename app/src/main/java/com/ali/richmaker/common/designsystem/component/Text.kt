package com.ali.richmaker.common.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CurrencyText(
    amount: Double = 0.0,
    fontSize: TextUnit = 12.sp,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = if (amount >= 0) Color.Black else MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier,

    ) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    val formatted = currencyFormatter.format(amount)

    Text(
        text = formatted, fontSize = fontSize, modifier = modifier, style = style, color = color
    )
}

@Composable
fun DateText(
    date: Date,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onTertiary,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("HH:mm - MMMM dd", Locale.getDefault())
    val dateString = dateFormat.format(date)
    Text(text = dateString, style = style, color = color, modifier = modifier)

}