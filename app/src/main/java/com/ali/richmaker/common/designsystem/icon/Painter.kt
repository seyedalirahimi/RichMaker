package com.ali.richmaker.common.designsystem.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
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
    val Allowance = @Composable { painterResource(id = R.drawable.ic_allowance) }
    val Apparel = @Composable { painterResource(id = R.drawable.ic_apparel) }
    val Beauty = @Composable { painterResource(id = R.drawable.ic_beauty) }
    val Bonus = @Composable { painterResource(id = R.drawable.ic_bonus) }
    val Cash = @Composable { painterResource(id = R.drawable.ic_cash) }
    val Culture = @Composable { painterResource(id = R.drawable.ic_culture) }
    val Food = @Composable { painterResource(id = R.drawable.ic_food) }
    val Gift = @Composable { painterResource(id = R.drawable.ic_gift) }
    val Health = @Composable { painterResource(id = R.drawable.ic_health) }
    val Household = @Composable { painterResource(id = R.drawable.ic_household) }
    val Other = @Composable { painterResource(id = R.drawable.ic_other) }
    val Pet = @Composable { painterResource(id = R.drawable.ic_pet) }
    val Salary = @Composable { painterResource(id = R.drawable.ic_salary) }
    val Transport = @Composable { painterResource(id = R.drawable.ic_transport) }
}


@Composable
fun RichMakerPainter.getCategoryIcon(
    cateName: String
) : @Composable () -> Painter = when(cateName){
    "Allowance" -> Allowance
    "Apparel" -> Apparel
    "Beauty" -> Beauty
    "Bonus" -> Bonus
    "Cash" -> Cash
    "Culture" -> Culture
    "Food" -> Food
    "Gift" -> Gift
    "Health" -> Health
    "Household" -> Household
    "Other" -> Other
    "Pet" -> Pet
    "Salary" -> Salary
    "Transport" -> Transport
    else -> Other
}