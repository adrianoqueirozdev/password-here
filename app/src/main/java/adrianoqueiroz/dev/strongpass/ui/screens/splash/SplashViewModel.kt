package adrianoqueiroz.dev.strongpass.ui.screens.splash

import adrianoqueiroz.dev.strongpass.data.constants.Routes
import adrianoqueiroz.dev.strongpass.data.service.AccountService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DELAY_START_ANIMATION = 200L
private const val DELAY_DURATION_ANIMATION = 3000L
private const val DELAY_END_ANIMATION = 600L

@HiltViewModel
class SplashViewModel @Inject constructor(private val accountService: AccountService) : ViewModel() {

   private val _visible = MutableStateFlow(value = false)

   val visible = _visible

   fun onAppStart(openAdnPopUp: (String, String) -> Unit) {
      viewModelScope.launch {
         delay(timeMillis = DELAY_START_ANIMATION)
         _visible.update { true }
         delay(timeMillis = DELAY_DURATION_ANIMATION)
         _visible.update { false }
         delay(timeMillis = DELAY_END_ANIMATION)

         if (accountService.hasUser) {
            openAdnPopUp(Routes.HOME, Routes.SPLASH)
         } else {
            openAdnPopUp(Routes.LOGIN, Routes.SPLASH)
         }
      }
   }
}