package adrianoqueiroz.dev.strongpass.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackbar(snackbarHostState: SnackbarHostState) {
   SnackbarHost(
      hostState = snackbarHostState,
      modifier = Modifier.padding(8.dp),
      snackbar = { snackbarData ->
         Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
      },
   )
}