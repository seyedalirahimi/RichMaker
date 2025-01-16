package com.ali.richmaker.common.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ali.richmaker.common.designsystem.icon.RichMakerPainter
import com.ali.richmaker.common.designsystem.icon.getCategoryIcon
import java.util.Date


@Composable
fun TransactionItem(
    title: String, date: Date, category: String, amount: Double, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.onSecondary, RoundedCornerShape(percent = 35)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = RichMakerPainter.getCategoryIcon(category)(),
                tint = Color.White,
                contentDescription = null,
                modifier = modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        Column {
            Text(
                text = category, style = MaterialTheme.typography.bodyLarge
            )
            DateText(
                date = date,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = modifier
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        VerticalDivider(
            modifier = Modifier
                .width(4.dp)
                .height(40.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.weight(1f))
        VerticalDivider(
            modifier = Modifier
                .width(4.dp)
                .height(40.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.weight(1f))

        CurrencyText(
            amount = amount,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier

        )
    }
}

