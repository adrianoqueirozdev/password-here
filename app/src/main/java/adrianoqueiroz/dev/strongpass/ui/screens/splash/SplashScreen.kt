package adrianoqueiroz.dev.strongpass.ui.screens.splash

import adrianoqueiroz.dev.strongpass.ui.composable.AppLogo
import adrianoqueiroz.dev.strongpass.ui.theme.LightColorScheme
import adrianoqueiroz.dev.strongpass.ui.theme.StatusBarColor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
   splashViewModel: SplashViewModel = hiltViewModel(), openAndPopUp: (String, String) -> Unit
) {

   val visible by splashViewModel.visible.collectAsState()

   StatusBarColor(color = LightColorScheme.onPrimaryContainer, darkTheme = false)

   Scaffold(
      containerColor = LightColorScheme.onPrimaryContainer,
   ) {
      Column(
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally,
         modifier = Modifier
            .fillMaxSize()
            .padding(it)
      ) {
         AnimatedVisibility(visible, enter = expandIn(
            animationSpec = tween(durationMillis = 700, easing = LinearOutSlowInEasing),
            expandFrom = Alignment.BottomStart
         ) {
            IntSize(50, 50)
         }, exit = shrinkOut(
            tween(durationMillis = 400, easing = FastOutSlowInEasing),
            shrinkTowards = Alignment.CenterStart
         ) { fullSize ->
            IntSize(fullSize.width / 10, fullSize.height / 5)
         }) {
            AppLogo()
         }
      }
   }

   LaunchedEffect(true) {
      splashViewModel.onAppStart(openAndPopUp)
   }
}

@Preview
@Composable
fun SplashScreenPreview() {
   SplashScreen(openAndPopUp = { _, _ -> })
}