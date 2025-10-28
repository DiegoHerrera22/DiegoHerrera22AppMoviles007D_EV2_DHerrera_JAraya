package com.pasteleria1000sabores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pasteleria1000sabores.ui.navigation.AppNavGraph
import com.pasteleria1000sabores.ui.theme.Pasteleria1000SaboresTheme

/**
 * MainActivity is the entry point of the application.  It hosts the Compose
 * content via [setContent] and wraps the composable hierarchy in our custom
 * [Pasteleria1000SaboresTheme].  The navigation graph is defined in
 * [AppNavGraph], which handles routing between screens.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pasteleria1000SaboresTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph()
                }
            }
        }
    }
}