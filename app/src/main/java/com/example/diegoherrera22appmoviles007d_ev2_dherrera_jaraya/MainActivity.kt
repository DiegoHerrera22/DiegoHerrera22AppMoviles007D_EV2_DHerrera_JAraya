package com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.ui.theme.DiegoHerrera22AppMoviles007D_EV2_DHerrera_JArayaTheme
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.viewmodel.AuthViewModel
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.views.HomeScreen
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.views.LoginScreen
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.views.RegisterScreen
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.views.ProductDetailScreen
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.views.backoffice.BackOfficeListScreen
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.views.backoffice.AddProductScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiegoHerrera22AppMoviles007D_EV2_DHerrera_JArayaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation() // punto de entrada de la app
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login" // pantalla inicial
    ) {
        // Auth
        composable("login") {
            LoginScreen(navController = navController, viewModel = authViewModel)
        }
        composable("register") {
            RegisterScreen(navController = navController, viewModel = authViewModel)
        }

        // Catálogo (home) — usa tu ruta actual con email como argumento
        composable(
            route = "home/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            HomeScreen(email = email, navController = navController)
        }

        // Detalle de producto
        composable(
            route = "product/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            ProductDetailScreen(productId = id, navController = navController)
        }

        // Back Office (solo visual): lista de productos reutilizando el mismo JSON
        composable("backoffice") {
            BackOfficeListScreen(
                onAddProduct = { navController.navigate("backoffice/add") }
            )
        }

        // Pantalla para Agregar Producto (solo UI, sin persistencia)
        composable("backoffice/add") {
            AddProductScreen(navController = navController)
        }
    }
}
