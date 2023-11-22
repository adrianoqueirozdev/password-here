package adrianoqueiroz.dev.strongpass.ui.screens.password_detail

import adrianoqueiroz.dev.strongpass.R
import adrianoqueiroz.dev.strongpass.ui.composable.BackButton
import adrianoqueiroz.dev.strongpass.ui.composable.LoadingData
import adrianoqueiroz.dev.strongpass.ui.theme.StatusBarColor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordDetailScreen(
   navController: NavController,
   passwordDetailViewModel: PasswordDetailViewModel = hiltViewModel(),
) {

   val passwordDetailUiState by passwordDetailViewModel.passwordDetailUiState.collectAsState()
   val context = LocalContext.current.applicationContext

   StatusBarColor(
      color = MaterialTheme.colorScheme.surface,
      darkTheme = !isSystemInDarkTheme(),
   )

   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text(text = stringResource(R.string.details)) },
            navigationIcon = {
               BackButton(onClick = { navController.popBackStack() })
            },
            actions = {
               IconButton(onClick = { passwordDetailViewModel.togglePasswordVisibility() }) {
                  Icon(
                     imageVector = if (passwordDetailUiState.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                     contentDescription = null,
                  )
               }

               IconButton(onClick = { passwordDetailViewModel.onOpenAlertDialog() }) {
                  Icon(
                     imageVector = Icons.Default.Delete,
                     contentDescription = null,
                  )
               }
            },
         )
      },
   ) { innerPaddingModifier ->

      if (passwordDetailUiState.isLoading) {
         LoadingData(innerPaddingModifier = innerPaddingModifier)
      }

      if (passwordDetailUiState.password != null && passwordDetailUiState.isLoading.not()) {
         Column(
            modifier = Modifier
               .padding(innerPaddingModifier)
               .padding(16.dp)
               .fillMaxSize()
         ) {
            TitleDetail(text = stringResource(R.string.name))
            TitleValue(text = passwordDetailUiState.password!!.name)
            Spacer(modifier = Modifier.padding(8.dp))

            TitleDetail(text = stringResource(id = R.string.user_id))
            TitleValue(text = passwordDetailViewModel.getTextVisibility(passwordDetailUiState.password!!.userId))
            Spacer(modifier = Modifier.padding(8.dp))

            TitleDetail(text = stringResource(id = R.string.password))
            Row(
               modifier = Modifier.padding(top = 2.dp),
               verticalAlignment = Alignment.CenterVertically,
            ) {
               TitleValue(text = passwordDetailViewModel.getTextVisibility(passwordDetailUiState.password!!.password))

               Icon(
                  modifier = Modifier
                     .padding(start = 8.dp)
                     .clickable {
                        passwordDetailViewModel.copyToClipboard(
                           passwordDetailUiState.password!!.password, context
                        )
                     },
                  imageVector = passwordDetailUiState.iconCopy,
                  contentDescription = null,
               )
            }
         }

         if (passwordDetailUiState.openAlertDialog) {
            AlertDialogExample(
               onDismissRequest = { passwordDetailViewModel.onDismissRequest() },
               onConfirmation = {
                  passwordDetailViewModel.onClickDeletePassword(navController)
               },
               dialogTitle = stringResource(R.string.delete_password),
               dialogText = stringResource(R.string.delete_password_message),
               icon = Icons.Default.Delete,
            )
         }
      }
   }
}

@Composable
fun AlertDialogExample(
   onDismissRequest: () -> Unit,
   onConfirmation: () -> Unit,
   dialogTitle: String,
   dialogText: String,
   icon: ImageVector,
) {
   AlertDialog(
      containerColor = MaterialTheme.colorScheme.background,
      icon = { Icon(icon, contentDescription = null) },
      title = { Text(text = dialogTitle) },
      text = { Text(text = dialogText) },
      onDismissRequest = { onDismissRequest() },
      confirmButton = {
         TextButton(onClick = { onConfirmation() }) {
            Text(stringResource(R.string.confirm))
         }
      },
      dismissButton = {
         TextButton(onClick = { onDismissRequest() }) {
            Text(stringResource(R.string.cancel))
         }
      },
   )
}

@Composable
fun TitleDetail(text: String) {
   Text(
      text = text, style = MaterialTheme.typography.bodyLarge.copy(
         color = MaterialTheme.colorScheme.secondary,
      )
   )
}

@Composable
fun TitleValue(text: String) {
   Text(
      text = text,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      style = MaterialTheme.typography.bodyLarge,
   )
}

