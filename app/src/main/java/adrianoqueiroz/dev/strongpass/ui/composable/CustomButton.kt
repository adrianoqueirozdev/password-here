package adrianoqueiroz.dev.strongpass.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
   text: String,
   modifier: Modifier = Modifier,
   onClick: () -> Unit,
   enabled: Boolean = true
) {
   Button(
      onClick = onClick, modifier = modifier
         .fillMaxWidth()
         .padding(top = 24.dp)
         .height(56.dp),
      enabled = enabled
   ) {
      Text(text = text)
   }
}

@Preview
@Composable
fun CustomButtonPreview() {
   CustomButton(text = "Button", onClick = {})
}