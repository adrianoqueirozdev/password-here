package adrianoqueiroz.dev.strongpass.ui.screens.recover_password

import adrianoqueiroz.dev.strongpass.R
import adrianoqueiroz.dev.strongpass.ui.composable.AppLogo
import adrianoqueiroz.dev.strongpass.ui.composable.CustomButton
import adrianoqueiroz.dev.strongpass.ui.composable.CustomSnackbar
import adrianoqueiroz.dev.strongpass.ui.composable.InputTextField
import adrianoqueiroz.dev.strongpass.ui.theme.StatusBarColor
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverPasswordScreen(
   popUp: () -> Unit,
   recoverPasswordModel: RecoverPasswordViewModel = hiltViewModel(),
   snackbarHostState: SnackbarHostState,
) {
   val recoverPasswordUiState by recoverPasswordModel.recoverPasswordUiState.collectAsState()

   StatusBarColor(
      color = MaterialTheme.colorScheme.surface,
      darkTheme = !isSystemInDarkTheme(),
   )

   Scaffold(
      snackbarHost = {
         CustomSnackbar(snackbarHostState = snackbarHostState)
      },
      topBar = {
         TopAppBar(
            title = {},
            navigationIcon = {
               IconButton(onClick = { popUp() }) {
                  Icon(
                     imageVector = Icons.Default.ArrowBack,
                     contentDescription = null,
                  )
               }
            },
         )
      },
   ) { innerPaddingModifier ->
      Column(
         modifier = Modifier.padding(innerPaddingModifier)
      ) {
         if (recoverPasswordUiState.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
         }

         Column(
            modifier = Modifier
               .fillMaxSize()
               .padding(bottom = 64.dp, start = 24.dp, end = 24.dp)
               .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
         ) {

            AppLogo(
               colorFirstText = MaterialTheme.colorScheme.onBackground,
               colorSecondText = MaterialTheme.colorScheme.primary
            )

            Text(
               text = stringResource(R.string.description_recover_password),
               modifier = Modifier.fillMaxWidth(),
               textAlign = TextAlign.Start,
               style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.padding(6.dp))

            InputTextField(readOnly = recoverPasswordUiState.isLoading,
               value = recoverPasswordUiState.email,
               onValueChange = { recoverPasswordModel.onEmailChanged(it) },
               label = stringResource(R.string.email),
               isError = recoverPasswordUiState.errorMessageEmail.isNotEmpty(),
               supportingText = {
                  if (recoverPasswordUiState.errorMessageEmail.isNotEmpty()) {
                     Text(text = recoverPasswordUiState.errorMessageEmail)
                  }
               },
               leadingIcon = {
                  Icon(
                     imageVector = Icons.Default.Email,
                     contentDescription = null,
                  )
               })

            Spacer(modifier = Modifier.padding(8.dp))

            CustomButton(
               text = stringResource(R.string.recover_password_button),
               enabled = recoverPasswordUiState.isLoading.not(),
               onClick = {
                  recoverPasswordModel.onRecoverPasswordClicked(
                     popUp, snackbarHostState
                  )
               },
            )

            Spacer(modifier = Modifier.padding(24.dp))
         }
      }
   }
}

@Preview
@Composable
fun RecoverPasswordScreenPreview() {
   RecoverPasswordScreen(popUp = {}, snackbarHostState = SnackbarHostState())
}