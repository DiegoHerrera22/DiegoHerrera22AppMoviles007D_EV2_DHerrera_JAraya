package com.pasteleria1000sabores.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pasteleria1000sabores.ui.screens.BackOfficeListScreen
import com.pasteleria1000sabores.ui.screens.BackOfficeNewScreen
import com.pasteleria1000sabores.ui.screens.CarritoScreen
import com.pasteleria1000sabores.ui.screens.CatalogoScreen
import com.pasteleria1000sabores.ui.screens.CompraExitosaScreen
import com.pasteleria1000sabores.ui.screens.CompraRechazadaScreen
import com.pasteleria1000sabores.ui.screens.DetalleProductoScreen
import com.pasteleria1000sabores.ui.screens.LoginScreen
import com.pasteleria1000sabores.ui.screens.RegistroScreen

/**
 * A sealed class defining all available navigation destinations in the app.  Each
 * destination has a unique [route] string which is used by the [NavHost]
 * to identify where to navigate.  When adding a new screen, define a new
 * object here.
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Registro : Screen("registro")
    object Catalogo : Screen("catalogo")
    object DetalleProducto : Screen("detalle/{productoId}") {
        fun createRoute(productoId: Int) = "detalle/$productoId"
    }
    object Carrito : Screen("carrito")
    object CompraExitosa : Screen("compra_exitosa")
    object CompraRechazada : Screen("compra_rechazada")
    object BackOfficeList : Screen("backoffice/list")
    object BackOfficeNew : Screen("backoffice/new")
}

/**
 * Sets up the app's navigation graph.  The [NavHost] defines the starting
 * destination and maps each route to the corresponding screen composable.
 */
@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Catalogo.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }, onNavigateToRegister = {
                navController.navigate(Screen.Registro.route)
            })
        }
        composable(Screen.Registro.route) {
            RegistroScreen(onRegisterSuccess = {
                navController.navigate(Screen.Catalogo.route) {
                    popUpTo(Screen.Registro.route) { inclusive = true }
                }
            }, onNavigateBack = {
                navController.popBackStack()
            })
        }
        composable(Screen.Catalogo.route) {
            CatalogoScreen(onProductoClick = { productoId ->
                navController.navigate(Screen.DetalleProducto.createRoute(productoId))
            }, onCartClick = {
                navController.navigate(Screen.Carrito.route)
            }, onBackOfficeClick = {
                navController.navigate(Screen.BackOfficeList.route)
            })
        }
        composable(Screen.DetalleProducto.route) { backStackEntry ->
            // Obtain view models scoped to this navigation graph.  The cart
            // view model is shared across screens so the cart retains items
            // added from the detail screen.
            val catalogoViewModel: com.pasteleria1000sabores.viewmodel.CatalogoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val cartViewModel: com.pasteleria1000sabores.viewmodel.CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val id = backStackEntry.arguments?.getString("productoId")?.toIntOrNull()
            DetalleProductoScreen(
                productoId = id,
                onAddToCart = {
                    // Look up the product and add it to the cart.
                    id?.let { pid ->
                        val producto = catalogoViewModel.productos.value.firstOrNull { it.id == pid }
                        if (producto != null) {
                            cartViewModel.addToCart(producto)
                        }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Carrito.route) {
            // Share the cart view model across screens so that items persist
            val cartViewModel: com.pasteleria1000sabores.viewmodel.CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            CarritoScreen(
                onCompra = { success ->
                    if (success) {
                        navController.navigate(Screen.CompraExitosa.route) {
                            popUpTo(Screen.Carrito.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.CompraRechazada.route) {
                            popUpTo(Screen.Carrito.route) { inclusive = true }
                        }
                    }
                },
                onBack = { navController.popBackStack() },
                viewModel = cartViewModel
            )
        }
        composable(Screen.CompraExitosa.route) {
            CompraExitosaScreen(onContinue = {
                navController.navigate(Screen.Catalogo.route) {
                    popUpTo(Screen.CompraExitosa.route) { inclusive = true }
                }
            })
        }
        composable(Screen.CompraRechazada.route) {
            CompraRechazadaScreen(onContinue = {
                navController.navigate(Screen.Catalogo.route) {
                    popUpTo(Screen.CompraRechazada.route) { inclusive = true }
                }
            })
        }
        composable(Screen.BackOfficeList.route) {
            BackOfficeListScreen(onAddProducto = {
                navController.navigate(Screen.BackOfficeNew.route)
            }, onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.BackOfficeNew.route) {
            BackOfficeNewScreen(onProductoCreated = {
                navController.popBackStack()
            }, onNavigateBack = { navController.popBackStack() })
        }
    }
}