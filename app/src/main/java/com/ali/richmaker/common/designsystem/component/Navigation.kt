package com.ali.richmaker.common.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ali.richmaker.common.designsystem.icon.RichMakerPainter
import com.ali.richmaker.common.designsystem.theme.RichMakerTheme


@Composable
fun RichMakerNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(

        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .height(110.dp),
        containerColor = RichMakerNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content,
    )
}

@Composable
fun RowScope.RichMakerNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = RichMakerNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}


@Preview
@Composable
fun RichMakerNavigationBarPreview() {
    val items = listOf("Home", "Analysis", "Categories")
    val icons = listOf(
        RichMakerPainter.Home,
        RichMakerPainter.Analysis,
        RichMakerPainter.Categories,

        )

    RichMakerTheme {
        RichMakerNavigationBar {
            items.forEachIndexed { index, item ->
                RichMakerNavigationBarItem(
                    icon = {
                        Icon(
                            painter = icons[index](),
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

object RichMakerNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.secondary

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.tertiary

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.tertiary
}