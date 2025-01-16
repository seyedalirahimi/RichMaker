package com.ali.richmaker.common.designsystem.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.ali.richmaker.R

object RichMakerPainter{
    val Home = @Composable { painterResource(id = R.drawable.ic_home) }
    val Categories = @Composable { painterResource(id = R.drawable.ic_categories) }
    val Analysis = @Composable { painterResource(id = R.drawable.ic_analysis) }
    val Profile = @Composable { painterResource(id = R.drawable.ic_profile) }
    val Transaction = @Composable { painterResource(id = R.drawable.ic_transaction) }
    val Income = @Composable { painterResource(id = R.drawable.ic_income) }
    val Expose = @Composable { painterResource(id = R.drawable.ic_expose) }
}