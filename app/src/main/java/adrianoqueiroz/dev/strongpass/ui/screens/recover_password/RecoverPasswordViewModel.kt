package adrianoqueiroz.dev.strongpass.ui.screens.recover_password

import adrianoqueiroz.dev.strongpass.common.ext.isValidateEmail
import adrianoqueiroz.dev.strongpass.data.model.ui_state.RecoverPasswordUiState
import adrianoqueiroz.dev.strongpass.common.utils.Validations
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
class RecoverPasswordViewModel @Inject constructor(private val accountService: AccountService) :
   ViewModel() {
   private val _recoverPasswordUiState = MutableStateFlow(RecoverPasswordUiState())

   val recoverPasswordUiState = _recoverPasswordUiState

   fun onEmailChanged(email: String) {
      _recoverPasswordUiState.value = _recoverPasswordUiState.value.copy(email = email)
   }

   fun onRecoverPasswordClicked(popUp: () -> Unit, snackbarHostState: SnackbarHostState) {
      if (validateForm()) {
         setLoading(true)
         viewModelScope.launch {
            try {
               accountService.sendPasswordResetEmail(_recoverPasswordUiState.value.email)
               popUp()
               setLoading(false)
            } catch (e: Exception) {
               setLoading(false)
               snackbarHostState.showSnackbar(message = "Email não encontrado")
            }
         }
      }
   }

   private fun setLoading(isLoading: Boolean) {
      _recoverPasswordUiState.update { currentState -> currentState.copy(isLoading = isLoading) }
   }

   private fun validateForm(): Boolean {
      val email = _recoverPasswordUiState.value.email

      val isEmailValid = email.isNotEmpty() && email.isValidateEmail()

      _recoverPasswordUiState.update { currentState ->
         currentState.copy(
            errorMessageEmail = Validations.errorMessageEmail(email)
         )
      }

      return isEmailValid
   }
}