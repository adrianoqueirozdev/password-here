package adrianoqueiroz.dev.strongpass.ui.screens.login

import adrianoqueiroz.dev.strongpass.R
import adrianoqueiroz.dev.strongpass.ui.composable.AppLogo
import adrianoqueiroz.dev.strongpass.ui.composable.CustomButton
import adrianoqueiroz.dev.strongpass.ui.composable.CustomSnackbar
import adrianoqueiroz.dev.strongpass.ui.composable.InputTextField
import adrianoqueiroz.dev.strongpass.ui.composable.LoadingLinearProgressIndicator
import adrianoqueiroz.dev.strongpass.ui.theme.StatusBarColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
   loginViewModel: LoginViewModel = hiltViewModel(),
   openAndPopUp: (String, String) -> Unit,
   navigate: (String) -> Unit,
   snackbarHostState: SnackbarHostState,
) {

   val loginUiState by loginViewModel.loginUiState.collectAsState()
   var passwordVisible by rememberSaveable { mutableStateOf(false) }

   StatusBarColor(
      color = MaterialTheme.colorScheme.surface,
      darkTheme = !isSystemInDarkTheme(),
   )

   Scaffold(
      snackbarHost = {
         CustomSnackbar(snackbarHostState = snackbarHostState)
      },
   ) { innerPaddingModifier ->
      if (loginUiState.isLoading) LoadingLinearProgressIndicator()

      Column(
         modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
            .padding(innerPaddingModifier)
            .verticalScroll(state = rememberScrollState()),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally
      ) {

         AppLogo(
            colorFirstText = MaterialTheme.colorScheme.onBackground,
            colorSecondText = MaterialTheme.colorScheme.primary
         )

         InputTextField(value = loginUiState.email,
            readOnly = loginUiState.isLoading,
            onValueChange = { loginViewModel.onEmailChanged(it) },
            label = stringResource(R.string.email),
            isError = loginUiState.errorMessageEmail.isNotEmpty(),
            keyboardOptions = KeyboardOptions(
               keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            supportingText = {
               if (loginUiState.errorMessageEmail.isNotEmpty()) {
                  Text(text = loginUiState.errorMessageEmail)
               }
            },
            leadingIcon = {
               Icon(
                  imageVector = Icons.Default.Email,
                  contentDescription = stringResource(R.string.email),
               )
            })

         Spacer(modifier = Modifier.padding(8.dp))

         InputTextField(value = loginUiState.password,
            readOnly = loginUiState.isLoading,
            onValueChange = { loginViewModel.onPasswordChanged(it) },
            label = stringResource(R.string.password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = loginUiState.errorMessagePassword.isNotEmpty(),
            keyboardOptions = KeyboardOptions(
               keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Go
            ),
            supportingText = {
               if (loginUiState.errorMessagePassword.isNotEmpty()) {
                  Text(text = loginUiState.errorMessagePassword)
               }
            },
            leadingIcon = {
               Icon(
                  imageVector = Icons.Default.Lock,
                  contentDescription = stringResource(R.string.password),
               )
            },
            trailingIcon = {
               IconButton(onClick = { passwordVisible = !passwordVisible }) {
                  Icon(
                     imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                     contentDescription = stringResource(R.string.password),
                  )
               }
            })

         Spacer(modifier = Modifier.padding(4.dp))

         Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
         ) {
            Text(
               modifier = Modifier.clickable(enabled = loginUiState.isLoading.not()) {
                  loginViewModel.onNavigateToRecoverPasswordClicked(navigate)
               },
               text = stringResource(R.string.recover_password),
               textAlign = TextAlign.End,
               style = MaterialTheme.typography.titleSmall,
            )
         }

         CustomButton(enabled = loginUiState.isLoading.not(),
            text = stringResource(R.string.to_enter),
            onClick = { loginViewModel.onLoginClicked(openAndPopUp, snackbarHostState) })

         Spacer(modifier = Modifier.padding(24.dp))
      }
   }
}
