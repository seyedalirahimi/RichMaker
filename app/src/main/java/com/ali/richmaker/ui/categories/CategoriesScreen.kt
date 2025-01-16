package com.ali.richmaker.ui.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ali.richmaker.common.designsystem.component.CurrencyText
import com.ali.richmaker.common.designsystem.component.RichMakerIconButton
import com.ali.richmaker.common.designsystem.component.RichMakerProgressIndicator
import com.ali.richmaker.common.designsystem.component.RichMakerTopAppBar
import com.ali.richmaker.common.designsystem.icon.RichMakerPainter
import com.ali.richmaker.common.designsystem.icon.getCategoryIcon

@Composable
fun CategoriesRoute(
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
    onCategoryClick: (Int) -> Unit,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val categoriesUiState = categoriesViewModel.state.collectAsStateWithLifecycle()

    CategoriesScreen(
        uiState = categoriesUiState.value,
        onCategoryClick = onCategoryClick,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
fun CategoriesScreen(
    uiState: CategoriesUiState = CategoriesUiState(),
    onCategoryClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.tertiary
            ),
    ) {
        RichMakerTopAppBar(
            title = "Categories",
            onBackClick = onBackClick,
        )

        Column(
            modifier = Modifier.padding(horizontal = 48.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BalanceItem(
                    title = "Total Balance",
                    amount = uiState.totalBalance,
                )

                VerticalDivider(
                    modifier = Modifier
                        .width(4.dp)
                        .height(40.dp),
                    color = Color.White
                )

                BalanceItem(
                    title = "Total Expense",
                    amount = uiState.totalExpense,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val currentAmount = uiState.totalBalance + uiState.totalExpense
            RichMakerProgressIndicator(
                currentAmount = currentAmount,
                maxAmount = uiState.goal,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(topStartPercent = 15, topEndPercent = 15)
                )
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.categories.size) { index ->
                println(index)
                val category = uiState.categories[index]
                RichMakerIconButton(
                    label = category.name,
                    icon = RichMakerPainter.getCategoryIcon(category.name)(),
                    onClick = { onCategoryClick(category.id) },
                    modifier = Modifier
                )

            }
        }
    }
}


@Composable
fun BalanceItem(
    title: String,
    amount: Double
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val isExpense = amount < 0

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = if (isExpense) RichMakerPainter.Expense() else RichMakerPainter.Income(),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        CurrencyText(
            amount = amount,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 18.sp,
            color = if (isExpense) Color.Blue else Color.White,
        )
    }
}