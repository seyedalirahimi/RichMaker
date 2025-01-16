package com.ali.richmaker.common.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichMakerTopAppBar(
    title: String = "", onBackClick: () -> Unit = {}, onNotificationClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.tertiary)
            .fillMaxWidth()
            .height(64.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        IconButton(
            onClick = onBackClick,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                tint = Color.White,
                contentDescription = "Back"
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        IconButton(
            onClick = onNotificationClick,
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                tint = Color.White,
                contentDescription = "Notifications"
            )
        }
    }


//    CenterAlignedTopAppBar(
//        modifier = Modifier.height(80.dp),
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = MaterialTheme.colorScheme.tertiary,
//            titleContentColor = Color.White,
//        ), title = {
//
//        }, navigationIcon = {
//
//        }, actions = {
//
//        })
}