package com.example.sorty2.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sorty2.Screen
import com.example.sorty2.screens.MyTopBar

// This is the home screen.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    onFinish: () -> Unit
) {
    BackHandler { onFinish() }
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                MyTopBar(
                    screen = "Home",
                    isNavItem = true,
                    navController = navController,
                    Button = {
                        HomeActionButton(navController)
                    }
                )
            }
        ) { values ->
            Column(
                Modifier
                    .padding(values)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Upcoming Tasks",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                HomeListsCard(
                    title = "Bacon ipsum",
                    description = "Meatball tongue biltong pork loin drumstick sausage hamburger burgdoggen.",
                    modifier = Modifier.padding(6.dp)
                )
                Text(
                    text = "Expenses",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                LazyRow(
                    content = {
                        items(10) {
                            ImageCard(
                                name = "Bacon ipsum",
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                )
                Text(
                    text = "Shopping List",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                HomeListsCard(
                    title = "Bacon ipsum",
                    description = "Meatball corned beef swine, hamburger burgdoggen.",
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}

@Composable
fun HomeActionButton(navController: NavHostController) {
    IconButton(onClick = {
        navController.navigate(Screen.Settings.route.routeString)
    }) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(
    name = "Home Screen",
    showBackground = true
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onFinish = {}, navController = rememberNavController())
}