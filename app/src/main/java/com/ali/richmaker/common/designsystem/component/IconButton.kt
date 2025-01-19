package com.ali.richmaker.common.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ali.richmaker.common.designsystem.icon.RichMakerPainter
import com.ali.richmaker.common.designsystem.theme.RichMakerTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun RichMakerIconButton(
    label: String = "Button",
    icon: Painter = RichMakerPainter.Home(),
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(MaterialTheme.colorScheme.onSecondary, RoundedCornerShape(8.dp))
                .align(Alignment.CenterHorizontally),
        ) {
            Icon(
                painter = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }

        Text(
            text = label,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview
@Composable
private fun RichMakerIconButtonPreview() {
    RichMakerIconButton()
}

@Composable
fun FinancialIconButton(
    label: String = "Financial Icon Button",
    amount: Double = 1.0,
    isEnable: Boolean = true,
    hasIcon: Boolean = true,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    val formatted = currencyFormatter.format(amount)

    Card(colors = CardDefaults.cardColors(
        containerColor = if (isEnable) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.primary
    ), modifier = modifier.clickable { onClick() }) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val isIncome = amount >= 0
            if (hasIcon) {
                Icon(
                    painter = if (isIncome) RichMakerPainter.Income() else RichMakerPainter.Expense(),
                    contentDescription = label,
                    tint = if (isEnable) Color.White else if (isIncome) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.size(20.dp)
                )
            }
            val textColor: Color = if (isEnable) {
                Color.White
            } else {
                Color.Black
            }

            Text(
                text = label, fontSize = 12.sp, fontWeight = FontWeight.Normal, color = textColor
            )

            Text(
                text = formatted,
                fontSize = 16.sp,
                modifier = Modifier.padding(0.dp),
                fontWeight = FontWeight.ExtraBold,
                color = textColor
            )
        }
    }
}

@Preview
@Composable
private fun FinancialIconButtonPreview() {
    RichMakerTheme {
        FinancialIconButton()
    }
}