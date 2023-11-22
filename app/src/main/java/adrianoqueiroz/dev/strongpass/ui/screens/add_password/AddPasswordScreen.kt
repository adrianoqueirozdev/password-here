package adrianoqueiroz.dev.strongpass.ui.screens.add_password

import adrianoqueiroz.dev.strongpass.R
import adrianoqueiroz.dev.strongpass.ui.composable.BackButton
import adrianoqueiroz.dev.strongpass.ui.composable.CustomButton
import adrianoqueiroz.dev.strongpass.ui.composable.InputTextField
import adrianoqueiroz.dev.strongpass.ui.theme.StatusBarColor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPasswordScreen(
   navController: NavController,
   addPasswordViewModel: AddPasswordViewModel = hiltViewModel()
) {

   val addPasswordUiState by addPasswordViewModel.addPasswordUiState.collectAsState()

   StatusBarColor(
      color = MaterialTheme.colorScheme.surface,
      darkTheme = !isSystemInDarkTheme(),
   )

   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text(text = stringResource(R.string.new_entry)) },
            navigationIcon = {
               BackButton(onClick = { navController.popBackStack() })
            },
         )
      },
   ) { innerPaddingModifier ->
      Column(
         modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp)
            .padding(innerPaddingModifier)
            .verticalScroll(state = rememberScrollState()),
      ) {

         if (addPasswordUiState.isLoading) {
            LinearProgressIndicator(
               modifier = Modifier
                  .fillMaxWidth()
            )
         }

         Column(
            modifier = Modifier
               .padding(start = 16.dp, end = 16.dp)
               .fillMaxSize()
         ) {
            InputTextField(
               readOnly = addPasswordUiState.isLoading,
               value = addPasswordUiState.name,
               onValueChange = { addPasswordViewModel.onNameChanged(it) },
               label = stringResource(R.string.name),
               isError = addPasswordUiState.errorMessageName.isNotEmpty(),
               supportingText = {
                  if (addPasswordUiState.errorMessageName.isNotEmpty()) {
                     Text(text = addPasswordUiState.errorMessageName)
                  }
               },
            )

            Spacer(modifier = Modifier.padding(8.dp))

            InputTextField(
               readOnly = addPasswordUiState.isLoading,
               value = addPasswordUiState.idUser,
               isError = addPasswordUiState.errorMessageIdUser.isNotEmpty(),
               onValueChange = { addPasswordViewModel.onEmailChanged(it) },
               label = stringResource(R.string.user_id),
               supportingText = {
                  if (addPasswordUiState.errorMessageIdUser.isNotEmpty()) {
                     Text(text = addPasswordUiState.errorMessageIdUser)
                  }
               },
            )

            Spacer(modifier = Modifier.padding(8.dp))

            InputTextField(
               readOnly = addPasswordUiState.isLoading,
               value = addPasswordUiState.password,
               onValueChange = { addPasswordViewModel.onPasswordChanged(it) },
               label = stringResource(R.string.password),
               isError = addPasswordUiState.errorMessagePassword.isNotEmpty(),
               supportingText = {
                  if (addPasswordUiState.errorMessagePassword.isNotEmpty()) {
                     Text(text = addPasswordUiState.errorMessagePassword)
                  }
               },
            )

            Spacer(modifier = Modifier.padding(16.dp))

            CustomButton(
               enabled = addPasswordUiState.isLoading.not(),
               text = stringResource(R.string.save),
               onClick = { addPasswordViewModel.onAddPasswordClicked(navController) }
            )
         }
      }
   }
}
