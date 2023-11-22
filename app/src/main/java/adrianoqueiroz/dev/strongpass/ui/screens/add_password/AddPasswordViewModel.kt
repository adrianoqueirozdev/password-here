package adrianoqueiroz.dev.strongpass.ui.screens.add_password

import adrianoqueiroz.dev.strongpass.common.utils.EncryptData
import adrianoqueiroz.dev.strongpass.common.utils.Validations
import adrianoqueiroz.dev.strongpass.data.constants.CommonConstants
import adrianoqueiroz.dev.strongpass.data.constants.Routes
import adrianoqueiroz.dev.strongpass.data.model.Password
import adrianoqueiroz.dev.strongpass.data.model.ui_state.AddPasswordUiState
import adrianoqueiroz.dev.strongpass.data.service.AccountService
import adrianoqueiroz.dev.strongpass.data.service.PasswordService
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddPasswordViewModel @Inject constructor(
   savedStateHandle: SavedStateHandle,
   private val passwordService: PasswordService,
   private val accountService: AccountService,
) : ViewModel() {
   private val _addPasswordUiState = MutableStateFlow(AddPasswordUiState())
   val addPasswordUiState = _addPasswordUiState

   init {
      val param = savedStateHandle.get<String>(Routes.Params.CATEGORY_ID) ?: ""

      if (param.isNotEmpty()) {
         _addPasswordUiState.update { currentState ->
            currentState.copy(passwordCategoryId = param)
         }
      }
   }

   fun onNameChanged(name: String) {
      _addPasswordUiState.update { currentState -> currentState.copy(name = name) }
   }

   fun onEmailChanged(idUser: String) {
      _addPasswordUiState.update { currentState -> currentState.copy(idUser = idUser) }
   }

   fun onPasswordChanged(password: String) {
      _addPasswordUiState.update { currentState -> currentState.copy(password = password) }
   }

   @RequiresApi(Build.VERSION_CODES.O)
   fun onAddPasswordClicked(navController: NavController) {
      if (validateForm()) {
         setLoading(true)

         val password = Password(
            currentUser = accountService.currentUserId,
            name = EncryptData.encryptAES(_addPasswordUiState.value.name),
            userId = EncryptData.encryptAES(_addPasswordUiState.value.idUser),
            password = EncryptData.encryptAES(_addPasswordUiState.value.password),
            passwordCategoryId = _addPasswordUiState.value.passwordCategoryId,
            createdAt = dateNow(),
         )

         viewModelScope.launch {
            try {
               passwordService.createPassword(password)
               setLoading(false)
               navController.previousBackStackEntry?.savedStateHandle?.set(
                  CommonConstants.UPDATE,
                  true
               )
               navController.popBackStack()
            } catch (e: Exception) {
               setLoading(false)
            }
         }
      }
   }

   private fun setLoading(isLoading: Boolean) {
      _addPasswordUiState.update { currentState ->
         currentState.copy(isLoading = isLoading)
      }
   }

   private fun validateForm(): Boolean {
      val name = _addPasswordUiState.value.name
      val idUser = _addPasswordUiState.value.idUser
      val password = _addPasswordUiState.value.password

      val isNameValid = name.isNotEmpty()
      val isIdUserValid = idUser.isNotEmpty()
      val isPasswordValid = password.isNotEmpty()

      _addPasswordUiState.update { currentState ->
         currentState.copy(
            errorMessageName = Validations.errorMessageName(name),
            errorMessageIdUser = Validations.errorMessageIdUser(idUser),
            errorMessagePassword = Validations.errorMessageAddPassword(password)
         )
      }

      return isNameValid && isIdUserValid && isPasswordValid
   }

   @RequiresApi(Build.VERSION_CODES.O)
   private fun dateNow(): String {
      val dataHoraAtual = LocalDateTime.now()

      val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
      return dataHoraAtual.format(formato)
   }
}