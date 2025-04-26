package com.example.carrentalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carrentalapp.screens.add_car_page.AddCarPageScreen
import com.example.carrentalapp.screens.book_car_page.BookCarPageScreen
import com.example.carrentalapp.screens.bookings_history_page.BookingsHistoryPageScreen
import com.example.carrentalapp.screens.bookings_page.BookingPageScreen
import com.example.carrentalapp.screens.buy_stock_page.BuyStockPageScreen
import com.example.carrentalapp.screens.create_user_page.CreateUserPageScreen
import com.example.carrentalapp.screens.depot_page.DepotPageScreen
import com.example.carrentalapp.screens.display_users_page.DisplayUsersPageScreen
import com.example.carrentalapp.screens.employee_page.EmployeePageScreen
import com.example.carrentalapp.screens.home_page.HomePageScreen
import com.example.carrentalapp.screens.sell_stock_page.SellStockPageScreen
import com.example.carrentalapp.screens.user_page.UserPageScreen
import com.example.carrentalapp.ui.theme.CarRentalTheme
import com.example.carrentalapp.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarRentalTheme {
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
                            //Todo mb delete an ID since there is only one admin rn
                            route = Routes.ADMIN_PAGE + "?id={id}",
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
                            route = Routes.REGISTER_PAGE
                        ) {
                            CreateUserPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = Routes.DISPLAY_USERS_PAGE
                        ) {
                            DisplayUsersPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = Routes.ADD_CAR_PAGE
                        ) {
                            AddCarPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = Routes.DISPLAY_USER_ACTIVE_BOOKINGS_PAGE
                        ) {
                            BookingPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(
                            route = Routes.DISPLAY_USER_BOOKINGS_HISTORY_PAGE
                        ) {
                            BookingsHistoryPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(
                            route = Routes.BOOK_CAR_PAGE
                        ) {
                            BookCarPageScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
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