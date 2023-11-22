package adrianoqueiroz.dev.strongpass.ui.composable

import adrianoqueiroz.dev.strongpass.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoadingData(innerPaddingModifier: PaddingValues) {
   Column(
      modifier = androidx.compose.ui.Modifier
         .padding(innerPaddingModifier)
         .padding(bottom = 64.dp)
         .fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
   ) {
      CircularProgressIndicator()
      Spacer(modifier = Modifier.height(8.dp))
      Text(
         text = stringResource(R.string.loading_data_message),
         textAlign = TextAlign.Center,
      )
   }
}