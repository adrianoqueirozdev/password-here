package adrianoqueiroz.dev.strongpass.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingLinearProgressIndicator(innerPaddingModifier: PaddingValues = PaddingValues(0.dp)) {
   Box(modifier = Modifier
      .fillMaxSize()
      .padding(innerPaddingModifier)) {
      LinearProgressIndicator(
         modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)
      )
   }
}

@Preview
@Composable
fun LoadingPreview() {
   LoadingLinearProgressIndicator()
}