package com.ali.richmaker.common.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ali.richmaker.common.designsystem.theme.RichMakerTheme

@Composable
fun RichMakerProgressIndicator(
    currentAmount: Double = 1000.0, maxAmount: Double = 4500.0, modifier: Modifier = Modifier
) {
    val progress: Float = (currentAmount / maxAmount).toFloat()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        // Background Bar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp))
        )

        // Progress Bar
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(16.dp))
        )

        // Percentage Text (Inside, at the start of the progress bar)
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp)
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd // Align to the right dynamically
        ) {
            CurrencyText(
                amount = currentAmount,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun RichMakerProgressIndicatorPreview() {
    RichMakerTheme {
        RichMakerProgressIndicator()
    }
}
