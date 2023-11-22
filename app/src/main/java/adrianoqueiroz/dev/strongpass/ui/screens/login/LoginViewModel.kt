package adrianoqueiroz.dev.strongpass.ui.screens.login

import adrianoqueiroz.dev.strongpass.common.ext.isValidateEmail
import adrianoqueiroz.dev.strongpass.common.ext.isValidatePassword
import adrianoqueiroz.dev.strongpass.common.utils.Validations
import adrianoqueiroz.dev.strongpass.data.constants.Routes
import adrianoqueiroz.dev.strongpass.data.model.ui_state.LoginUiState
import adrianoqueiroz.dev.strongpass.data.service.AccountService
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val accountService: AccountService) : ViewModel() {

   private val _loginUiState = MutableStateFlow(LoginUiState())
   val loginUiState = _loginUiState

   fun onEmailChanged(email: String) {
      _loginUiState.update { currentState ->
         currentState.copy(email = email)
      }
   }

   fun onPasswordChanged(password: String) {
      _loginUiState.update { currentState ->
         currentState.copy(password = password)
      }
   }

   fun onLoginClicked(
      openAndPopUp: (String, String) -> Unit, snackbarHostState: SnackbarHostState
   ) {
      if (validateForm()) {
         setLoading(true)

         viewModelScope.launch {
            try {
               accountService.authenticate(_loginUiState.value.email, _loginUiState.value.password)
               setLoading(false)
               openAndPopUp(Routes.HOME, Routes.LOGIN)
            } catch (e: Exception) {
               setLoading(false)
               snackbarHostState.showSnackbar(message = "Email ou senha inválidos")
            }
         }
      }
   }

   fun onNavigateToRecoverPasswordClicked(navigate: (String) -> Unit) {
      navigate(Routes.RECOVER_PASSWORD)
   }

   private fun setLoading(isLoading: Boolean) {
      _loginUiState.update { currentState -> currentState.copy(isLoading = isLoading) }
   }

   private fun validateForm(): Boolean {
      val email = _loginUiState.value.email
      val password = _loginUiState.value.password

      val isEmailValid = email.isNotEmpty() && email.isValidateEmail()
      val isPasswordValid = password.isNotEmpty() && password.isValidatePassword()

      _loginUiState.update { currentState ->
         currentState.copy(
            errorMessageEmail = Validations.errorMessageEmail(email),
            errorMessagePassword = Validations.errorMessagePassword(password)
         )
      }

      return isEmailValid && isPasswordValid
   }
}