package adrianoqueiroz.dev.strongpass.ui.screens.passwords

import adrianoqueiroz.dev.strongpass.R
import adrianoqueiroz.dev.strongpass.ui.composable.BackButton
import adrianoqueiroz.dev.strongpass.ui.composable.LoadingData
import adrianoqueiroz.dev.strongpass.ui.theme.StatusBarColor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordsScreen(
   isUpdate: Boolean = false,
   navController: NavController,
   popUp: () -> Unit,
   openScreen: (String) -> Unit,
   passwordsViewNodel: PasswordsViewNodel = hiltViewModel()
) {
   val passwordsUiState by passwordsViewNodel.passwordsUiState.collectAsState()

   StatusBarColor(
      color = MaterialTheme.colorScheme.surface,
      darkTheme = !isSystemInDarkTheme(),
   )

   LaunchedEffect(key1 = navController.currentBackStackEntry) {
      if (isUpdate) {
         passwordsViewNodel.getPasswordsByCategory(passwordsUiState.category.id)
      }
   }

   Scaffold(
      topBar = {
         TopAppBar(
            title = {
               Text(text = passwordsUiState.category.name)
            },
            navigationIcon = {
               BackButton(onClick = popUp)
            },
         )
      },
      floatingActionButton = {
         FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = { passwordsViewNodel.navigateToAddPassword(openScreen) },
         ) {
            Icon(
               imageVector = Icons.Default.Add,
               contentDescription = stringResource(R.string.add),
            )
         }
      },
   ) { innerPaddingModifier ->
      if (passwordsUiState.isLoadingPasswords) {
         LoadingData(innerPaddingModifier = innerPaddingModifier)
      }

      if (passwordsUiState.passwords.isEmpty() && passwordsUiState.isLoadingPasswords.not()) {
         Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
               .padding(innerPaddingModifier)
               .fillMaxSize(),
         ) {
            Text(
               text = stringResource(R.string.no_passwords_registered),
               textAlign = TextAlign.Center,
            )
         }
      }

      if (passwordsUiState.passwords.isNotEmpty() && passwordsUiState.isLoadingPasswords.not()) {

         LazyColumn(
            modifier = Modifier.padding(innerPaddingModifier),
            contentPadding = PaddingValues(bottom = 80.dp),
         ) {
            items(passwordsUiState.passwords) { password ->
               PasswordItem(
                  onClick = {
                     passwordsViewNodel.onPasswordClicked(openScreen, password.id)
                  },
                  title = password.name,
                  avatarText = password.name.first().toString(),
                  isLastItem = passwordsUiState.passwords.last() == password,
               )
            }
         }
      }
   }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PasswordItem(title: String, avatarText: String, onClick: () -> Unit, isLastItem: Boolean) {
   Column {
      ListItem(
         modifier = Modifier.clickable { onClick() },
         text = {
            Text(text = title)
         },
         icon = {
            Box(
               modifier = Modifier
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.surfaceVariant)
                  .height(48.dp)
                  .width(48.dp)
            ) {
               Text(
                  text = avatarText.uppercase(),
                  modifier = Modifier.align(alignment = Alignment.Center),
                  style = MaterialTheme.typography.titleMedium.copy(
                     fontWeight = FontWeight.Bold,
                  ),
               )
            }
         },
         trailing = {
            Icon(
               imageVector = Icons.Default.NavigateNext,
               contentDescription = null,
            )
         },
      )

      if (isLastItem.not()) {
         Divider(modifier = Modifier.padding(start = 72.dp, end = 16.dp))
      }
   }
}