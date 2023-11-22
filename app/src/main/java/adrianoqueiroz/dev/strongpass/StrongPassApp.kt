package adrianoqueiroz.dev.strongpass

import adrianoqueiroz.dev.strongpass.data.constants.CommonConstants
import adrianoqueiroz.dev.strongpass.data.constants.Routes
import adrianoqueiroz.dev.strongpass.ui.screens.add_password.AddPasswordScreen
import adrianoqueiroz.dev.strongpass.ui.screens.home.HomeScreen
import adrianoqueiroz.dev.strongpass.ui.screens.login.LoginScreen
import adrianoqueiroz.dev.strongpass.ui.screens.password_detail.PasswordDetailScreen
import adrianoqueiroz.dev.strongpass.ui.screens.passwords.PasswordsScreen
import adrianoqueiroz.dev.strongpass.ui.screens.recover_password.RecoverPasswordScreen
import adrianoqueiroz.dev.strongpass.ui.screens.splash.SplashScreen
import adrianoqueiroz.dev.strongpass.ui.theme.StrongPassTheme
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ExperimentalMaterial3Api
fun StrongPassApp() {
   StrongPassTheme {
      val appState = rememberAppState()

      NavHost(
         navController = appState.navController,
         startDestination = Routes.SPLASH,
      ) {
         strongPassGraph(appState = appState)
      }
   }
}

@Composable
fun rememberAppState(
   navController: NavHostController = rememberNavController(),
   snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) = remember(navController, snackbarHostState) {
   StrongPassAppState(
      navController = navController, snackbarHostState = snackbarHostState
   )
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
fun NavGraphBuilder.strongPassGraph(appState: StrongPassAppState) {
   composable(Routes.SPLASH) {
      SplashScreen(
         openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
      )
   }
   composable(Routes.LOGIN) {
      LoginScreen(
         openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
         navigate = { route -> appState.navigate(route) },
         snackbarHostState = appState.snackbarHostState,
      )
   }
   composable(Routes.RECOVER_PASSWORD) {
      RecoverPasswordScreen(
         popUp = { appState.popUp() },
         snackbarHostState = appState.snackbarHostState,
      )
   }
   composable(Routes.HOME) {
      HomeScreen(
         navController = appState.navController,
         openScreen = { route -> appState.navigate(route) },
         openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
      )
   }
   composable(
      route = "${Routes.PASSWORDS}${Routes.Params.CATEGORY_ID_ARG}",
      arguments = listOf(
         navArgument(Routes.Params.CATEGORY_ID) {
            nullable = true
            defaultValue = null
         },
      )
   ) {
      val isUpdate = it.savedStateHandle.get<Boolean>(CommonConstants.UPDATE)

      PasswordsScreen(
         isUpdate = isUpdate ?: false,
         navController = appState.navController,
         popUp = { appState.popUp() },
         openScreen = { route -> appState.navigate(route) },
      )
   }
   composable(
      route = "${Routes.ADD_PASSWORD}${Routes.Params.CATEGORY_ID_ARG}",
      arguments = listOf(
         navArgument(Routes.Params.CATEGORY_ID) {
            nullable = true
            defaultValue = null
         },
      )
   ) {
      AddPasswordScreen(navController = appState.navController)
   }

   composable(
      route = "${Routes.PASSWORD_DETAIL}${Routes.Params.PASSWORD_ID_ARG}",
      arguments = listOf(
         navArgument(Routes.Params.PASSWORD_ID) {
            nullable = true
            defaultValue = null
         },
      )
   ) {
      PasswordDetailScreen(navController = appState.navController)
   }
}