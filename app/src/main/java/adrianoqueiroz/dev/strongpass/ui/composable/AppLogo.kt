package adrianoqueiroz.dev.strongpass.ui.composable

import adrianoqueiroz.dev.strongpass.R
import adrianoqueiroz.dev.strongpass.ui.theme.LightColorScheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppLogo(
   colorFirstText: Color = LightColorScheme.onPrimary,
   colorSecondText: Color = LightColorScheme.primaryContainer
) {
   Box(
      modifier = Modifier
         .height(90.dp)
         .clip(shape = RoundedCornerShape(size = 8.dp))
   ) {
      Column() {
         Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(start = 6.dp, end = 6.dp)
         ) {
            Text(
               text = stringResource(R.string.password_title_1), style = MaterialTheme.typography.displaySmall.copy(
                  color = colorFirstText,
                  fontWeight = FontWeight.SemiBold
               )
            )
            Text(
               text = stringResource(R.string.password_title_2), style = MaterialTheme.typography.displaySmall.copy(
                  color = colorSecondText,
                  fontWeight = FontWeight.SemiBold
               )
            )
         }
      }
      Box(
         modifier = Modifier
            .padding(top = 46.dp)
            .width(32.dp)
            .height(4.dp)
            .clip(shape = RoundedCornerShape(size = 4.dp))
            .background(color = colorFirstText)
      ) {
      }
   }
}