package adrianoqueiroz.dev.strongpass.ui.screens.password_detail

import adrianoqueiroz.dev.strongpass.common.utils.EncryptData
import adrianoqueiroz.dev.strongpass.data.constants.CommonConstants
import adrianoqueiroz.dev.strongpass.data.constants.Routes
import adrianoqueiroz.dev.strongpass.data.model.ui_state.PasswordDetailUiState
import adrianoqueiroz.dev.strongpass.data.service.PasswordService
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PasswordDetailViewModel @Inject constructor(
   private val passwordService: PasswordService,
   savedStateHandle: SavedStateHandle,
) : ViewModel() {

   private val _passwordDetailUiState = MutableStateFlow(PasswordDetailUiState())
   val passwordDetailUiState = _passwordDetailUiState

   init {
      val passwordId = savedStateHandle.get<String>(Routes.Params.PASSWORD_ID) ?: ""

      if (passwordId != null) {
         getPasswordById(passwordId)
      }
   }

   fun onOpenAlertDialog() {
      _passwordDetailUiState.update { currentState ->
         currentState.copy(openAlertDialog = true)
      }
   }

   fun onDismissRequest() {
      _passwordDetailUiState.update { currentState ->
         currentState.copy(openAlertDialog = false)
      }
   }

   fun onClickDeletePassword(navController: NavController) {

      viewModelScope.launch {
         try {
            passwordService.deletePassword(passwordDetailUiState.value.password!!.id)
            onDismissRequest()
            navController.previousBackStackEntry?.savedStateHandle?.set(
               CommonConstants.UPDATE, true
            )
            navController.popBackStack()
         } catch (e: Exception) {
            onDismissRequest()
         }
      }
   }

   fun getTextVisibility(text: String): String {
      return if (passwordDetailUiState.value.isPasswordVisible) text else "********"
   }

   fun togglePasswordVisibility() {
      _passwordDetailUiState.update { currentState ->
         currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
      }
   }

   fun copyToClipboard(text: String, context: Context) {
      viewModelScope.launch {
         _passwordDetailUiState.update { currentState ->
            currentState.copy(iconCopy = Icons.Filled.Check)
         }

         val clipboard = ContextCompat.getSystemService(context, ClipboardManager::class.java)

         val clip = ClipData.newPlainText("label", text)
         clipboard?.setPrimaryClip(clip)

         delay(timeMillis = 3000)
         _passwordDetailUiState.update { currentState ->
            currentState.copy(iconCopy = Icons.Filled.ContentCopy)
         }
      }
   }

   private fun getPasswordById(passwordId: String) {
      setLoading(true)

      viewModelScope.launch {
         try {
            val password = passwordService.getPasswordById(passwordId)

            password.name = EncryptData.decryptAES(password.name)
            password.userId = EncryptData.decryptAES(password.userId)
            password.password = EncryptData.decryptAES(password.password)

            _passwordDetailUiState.update { currentState ->
               currentState.copy(password = password)
            }

            setLoading(false)
         } catch (e: Exception) {
            setLoading(false)
         }
      }
   }

   private fun setLoading(isLoading: Boolean) {
      _passwordDetailUiState.update { currentState ->
         currentState.copy(isLoading = isLoading)
      }
   }
}