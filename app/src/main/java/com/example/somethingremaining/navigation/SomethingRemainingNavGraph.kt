package com.example.somethingremaining.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.somethingremaining.ui.screen.HomeDestination
import com.example.somethingremaining.ui.screen.HomeScreen
import com.example.somethingremaining.ui.screen.NewToDoDestination
import com.example.somethingremaining.ui.screen.NewToDoScreen
import com.example.somethingremaining.ui.screen.SplashDestination
import com.example.somethingremaining.ui.screen.SplashScreen
import com.example.somethingremaining.ui.screen.ToDoUpdateDestination
import com.example.somethingremaining.ui.screen.ToDoUpdateScreen
import com.example.somethingremaining.ui.viewModel.UpdateToDoViewModel

@Composable
fun SomethingRemainingNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination.route,
        modifier = modifier
    ) {
        composable(route = SplashDestination.route) {
            SplashScreen(onTimeOut = { navController.navigate(HomeDestination.route)})
        }
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToNewToDo = { navController.navigate(NewToDoDestination.route) },
                navigateToUpdate = {
                    navController.navigate("${ToDoUpdateDestination.route}/${it}")
                }
            )
        }
        composable(route = NewToDoDestination.route) {
            NewToDoScreen(navigateBack = { navController.popBackStack() })
        }
        composable(route = ToDoUpdateDestination.routeWithArgs,
            arguments = listOf(navArgument(ToDoUpdateDestination.toDoIdArg) {
                type = NavType.IntType
            })
        ) {
            ToDoUpdateScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}