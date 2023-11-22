package adrianoqueiroz.dev.strongpass


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.WindowManager.LayoutParams
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
   @RequiresApi(Build.VERSION_CODES.O)
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      // Bloquear rotação da tela
      requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

      setContent {
         // Bloquear screenshots e visualização em multitarefa
         window.setFlags(LayoutParams.FLAG_SECURE, LayoutParams.FLAG_SECURE)

         StrongPassApp()
      }
   }
}