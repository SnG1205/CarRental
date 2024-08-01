package com.example.bachelorandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bachelorandroid.screens.buy_stock_page.BuyStockPageScreen
import com.example.bachelorandroid.screens.create_user_page.CreateUserPageScreen
import com.example.bachelorandroid.screens.depot_page.DepotPageScreen
import com.example.bachelorandroid.screens.display_clients_page.DisplayClientsPageScreen
import com.example.bachelorandroid.screens.employee_page.EmployeePageScreen
import com.example.bachelorandroid.screens.home_page.HomePageScreen
import com.example.bachelorandroid.screens.sell_stock_page.SellStockPageScreen
import com.example.bachelorandroid.screens.user_page.UserPageScreen
import com.example.bachelorandroid.ui.theme.BachelorAndroidTheme
import com.example.bachelorandroid.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BachelorAndroidTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME_PAGE
                    ) {
                        composable(Routes.HOME_PAGE) {
                            HomePageScreen(
                                onNavigate = {
                                    navController.navigate(it.route)
                                })
                        }
                        composable(
                            route = Routes.EMPLOYEE_PAGE + "?id={id}",
                            arguments = listOf(
                                navArgument(name = "id") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                }
                            )
                        ) {
                            EmployeePageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(
                            route = Routes.USER_PAGE + "?id={id}",
                            arguments = listOf(
                                navArgument(name = "id") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                }
                            )
                        ) {
                            UserPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(
                            route = Routes.CREATE_USER_PAGE
                        ) {
                            CreateUserPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = Routes.DISPLAY_CLIENTS_PAGE
                        ) {
                            DisplayClientsPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = Routes.BUY_STOCK_PAGE + "?clientId={clientId}",
                            arguments = listOf(
                                navArgument(name = "clientId") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                }
                            )
                        ) {
                            BuyStockPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = Routes.DEPOT_PAGE + "?clientId={clientId}",
                            arguments = listOf(
                                navArgument(name = "clientId") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                }
                            )
                        ) {
                            DepotPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(
                            route = Routes.SELL_STOCK_PAGE + "?clientId={clientId}" + "?symbols={symbols}",
                            arguments = listOf(
                                navArgument(name = "clientId") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                },
                                navArgument(name = "symbols") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )
                        ) {
                            SellStockPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}