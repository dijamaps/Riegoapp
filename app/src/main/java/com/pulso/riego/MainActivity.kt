package com.pulso.riego

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pulso.riego.data.AppDatabase
import com.pulso.riego.data.AppRepository
import com.pulso.riego.ui.HomeScreen
import com.pulso.riego.ui.LandingScreen
import com.pulso.riego.ui.LoginScreen
import com.pulso.riego.ui.theme.PulsoRiegoTheme
import com.pulso.riego.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pulso.riego.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    private val activityScope = CoroutineScope(SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getInstance(applicationContext, activityScope)
        val repo = AppRepository(db.appDao())

        setContent {
            PulsoRiegoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "landing") {
                    composable("landing") {
                        LandingScreen(
                            onLoginClick = { navController.navigate("login") },
                            onFeaturesClick = { navController.navigate("home") }
                        )
                    }
                    composable("login") {
                        val factory = LoginViewModelFactory(repo)
                        val vm: LoginViewModel = viewModel(factory = factory)
                        LoginScreen(
                            viewModel = vm,
                            onLoginSuccess = { navController.navigate("home") }
                        )
                    }
                    composable("home") {
                        HomeScreen()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLanding() {
    PulsoRiegoTheme {
        Surface {
            LandingScreen(onLoginClick = {}, onFeaturesClick = {})
        }
    }
}
