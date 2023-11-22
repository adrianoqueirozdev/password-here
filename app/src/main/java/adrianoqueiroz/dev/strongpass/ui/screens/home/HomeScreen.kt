package adrianoqueiroz.dev.strongpass.ui.screens.home

import adrianoqueiroz.dev.strongpass.common.utils.getIconCategory
import adrianoqueiroz.dev.strongpass.data.constants.Routes
import adrianoqueiroz.dev.strongpass.ui.composable.AppLogo
import adrianoqueiroz.dev.strongpass.ui.composable.LoadingData
import adrianoqueiroz.dev.strongpass.ui.theme.StatusBarColor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
   openScreen: (String) -> Unit,
   openAndPopUp: (String, String) -> Unit,
   navController: NavController,
   homeViewModel: HomeViewModel = hiltViewModel(),
) {
   val homeUiState by homeViewModel.homeUiState.collectAsState()

   StatusBarColor(
      color = MaterialTheme.colorScheme.surface,
      darkTheme = !isSystemInDarkTheme(),
   )

   val destination = navController.currentBackStackEntry?.destination?.route

   LaunchedEffect(key1 = destination == Routes.HOME) {
      homeViewModel.getCategories()
   }

   Scaffold(
      topBar = {
         TopAppBar(
            modifier = Modifier.height(64.dp),
            title = {
               AppLogo(
                  colorFirstText = MaterialTheme.colorScheme.onBackground,
                  colorSecondText = MaterialTheme.colorScheme.primary
               )
            },
            actions = {
               IconButton(onClick = { homeViewModel.logout(openAndPopUp) }) {
                  Icon(
                     imageVector = Icons.Default.Logout,
                     contentDescription = null,
                  )
               }
            }
         )
      },
   ) { innerPaddingModifier ->
      if (homeUiState.isLoading) {
         LoadingData(innerPaddingModifier = innerPaddingModifier)
      }

      if (homeUiState.categories.isNotEmpty() && !homeUiState.isLoading) {
         LazyColumn(
            modifier = Modifier
               .padding(innerPaddingModifier)
               .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
               .fillMaxSize()
         ) {
            items(homeUiState.categories) { category ->
               CategoryItem(
                  id = category.id,
                  title = category.name,
                  type = category.type,
                  totalPasswords = category.totalPasswords,
                  onClick = { categoryId ->
                     homeViewModel.navigateToPasswordsScreen(openScreen, categoryId)
                  },
               )
            }
         }
      }
   }
}

@Composable
fun CategoryItem(
   id: String,
   title: String,
   type: String,
   totalPasswords: Int,
   onClick: (categoryId: String) -> Unit,
) {
   ListItem(
      modifier = Modifier
         .padding(vertical = 6.dp)
         .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
            shape = MaterialTheme.shapes.medium
         )
         .clickable { onClick(id) },
      leadingContent = {
         Icon(
            imageVector = getIconCategory(type),
            contentDescription = title,
         )
      },
      headlineContent = {
         Text(text = title)
      },
      trailingContent = {
         if (totalPasswords > 0) {
            Text(
               text = totalPasswords.toString(),
               style = MaterialTheme.typography.titleMedium,
               color = MaterialTheme.colorScheme.error,
            )
         }
      },
   )
}

