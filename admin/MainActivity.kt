package com.example.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.admin.ui.*
import com.example.admin.ui.theme.AdminTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdminTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation(startDestination: String = "login") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onSignUpClick = { navController.navigate("signup") },
                onForgotPasswordClick = { navController.navigate("forgot_password_email") },
                onLoginSuccess = { navController.navigate("profile") }
            )
        }
        composable("signup") {
            SignUpScreen(
                onLoginClick = { navController.popBackStack() },
                onSignUpSuccess = { navController.navigate("profile") }
            )
        }
        composable("forgot_password_email") {
            ForgotPasswordEmailScreen(
                onSendCodeClick = { navController.navigate("forgot_password_method") }
            )
        }
        composable("forgot_password_method") {
            ForgotPasswordMethodScreen(
                onMethodSelected = { navController.navigate("verification_code") }
            )
        }
        composable("verification_code") {
            VerificationCodeScreen(
                onVerifyClick = { navController.navigate("create_new_password") }
            )
        }
        composable("create_new_password") {
            CreateNewPasswordScreen(
                onResetPasswordClick = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("profile") {
            ProfileScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@Preview(showBackground = true, name = "Login Screen")
@Composable
fun AppNavigationLoginPreview() {
    AdminTheme {
        AppNavigation("login")
    }
}

@Preview(showBackground = true, name = "Sign Up Screen")
@Composable
fun AppNavigationSignUpPreview() {
    AdminTheme {
        AppNavigation("signup")
    }
}

@Preview(showBackground = true, name = "Forgot Password Email Screen")
@Composable
fun AppNavigationForgotPasswordEmailPreview() {
    AdminTheme {
        AppNavigation("forgot_password_email")
    }
}

@Preview(showBackground = true, name = "Forgot Password Method Screen")
@Composable
fun AppNavigationForgotPasswordMethodPreview() {
    AdminTheme {
        AppNavigation("forgot_password_method")
    }
}

@Preview(showBackground = true, name = "Verification Code Screen")
@Composable
fun AppNavigationVerificationCodePreview() {
    AdminTheme {
        AppNavigation("verification_code")
    }
}

@Preview(showBackground = true, name = "Create New Password Screen")
@Composable
fun AppNavigationCreateNewPasswordPreview() {
    AdminTheme {
        AppNavigation("create_new_password")
    }
}

@Preview(showBackground = true, name = "Profile Screen")
@Composable
fun AppNavigationProfilePreview() {
    AdminTheme {
        AppNavigation("profile")
    }
}
