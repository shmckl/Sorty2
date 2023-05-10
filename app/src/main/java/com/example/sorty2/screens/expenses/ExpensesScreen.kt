package com.example.sorty2.screens.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sorty2.R
import com.example.sorty2.data.models.Bill
import com.example.sorty2.screens.BottomNavBar
import com.example.sorty2.screens.MyTopBar
import com.example.sorty2.screens.home.HomeActionButton
//import com.example.sorty2.ui.components.BillList

@Composable
fun ExpensesScreen(
    navController: NavHostController,
//    expensesViewModel: ExpensesViewModel = hiltViewModel()
) {
//    val bills: List<Bill> by expensesViewModel.bills.observeAsState(listOf())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                MyTopBar(
                    screen = "Expenses",
                    isNavItem = true,
                    navController = navController
                )
            },
            bottomBar = {
                BottomNavBar(navController = navController)
            }
        ) { values ->
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(8.dp))
//                BalanceInfo(expensesViewModel.totalBalance)
                Spacer(modifier = Modifier.height(16.dp))
//                BillList(bills = bills)
            }
        }
    }
}

//@Composable
//fun BalanceInfo(balance: Float) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            text = stringResource(R.string.balance),
//            style = MaterialTheme.typography.displayMedium
//        )
//        Text(
//            text = "%.2f".format(balance),
//            style = MaterialTheme.typography.displayMedium,
//            color = if (balance >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
//        )
//    }
//}
