package adrianoqueiroz.dev.strongpass.ui.screens.passwords

import adrianoqueiroz.dev.strongpass.common.ext.idFromParameter
import adrianoqueiroz.dev.strongpass.common.utils.EncryptData
import adrianoqueiroz.dev.strongpass.data.constants.Routes
import adrianoqueiroz.dev.strongpass.data.model.ui_state.PasswordsUiState
import adrianoqueiroz.dev.strongpass.data.service.CategoryService
import adrianoqueiroz.dev.strongpass.data.service.PasswordService
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PasswordsViewNodel @Inject constructor(
   savedStateHandle: SavedStateHandle,
   private val passwordService: PasswordService,
   private val categoryService: CategoryService,
) : ViewModel() {
   private val _passwordsUiState = MutableStateFlow(PasswordsUiState())
   val passwordsUiState = _passwordsUiState

   init {
      val param = savedStateHandle.get<String>(Routes.Params.CATEGORY_ID) ?: ""
      if (param != null) {
         val categoryId = param.idFromParameter()

         getCategory(categoryId)
         getPasswordsByCategory(categoryId)
      }
   }
   
   fun onPasswordClicked(openScreen: (String) -> Unit, passwordId: String) {
      openScreen("${Routes.PASSWORD_DETAIL}?${Routes.Params.PASSWORD_ID}=$passwordId")
   }

   fun navigateToAddPassword(openScreen: (String) -> Unit) {
      val categoryId = _passwordsUiState.value.category?.id
      openScreen("${Routes.ADD_PASSWORD}?${Routes.Params.CATEGORY_ID}=$categoryId")
   }

   fun getPasswordsByCategory(categoryId: String) {
      setLoadingPasswords(true)
      viewModelScope.launch {
         try {
            val passwords = passwordService.getPasswordsByCategory(categoryId)

            passwords.map { password ->
               password.name = EncryptData.decryptAES(password.name)
               password.userId = EncryptData.decryptAES(password.userId)
               password.password = EncryptData.decryptAES(password.password)
            }

            _passwordsUiState.update { currentState ->
               currentState.copy(passwords = passwords)
            }

            setLoadingPasswords(false)
         } catch (e: Exception) {
            setLoadingPasswords(false)
         }
      }
   }

   private fun getCategory(categoryId: String) {
      viewModelScope.launch {
         try {
            setLoadingCategory(true)
            val category = categoryService.getCategoryById(categoryId)
            _passwordsUiState.update { currentState ->
               currentState.copy(category = category)
            }
            setLoadingCategory(false)
         } catch (e: Exception) {
            setLoadingCategory(false)
         }
      }
   }

   private fun setLoadingPasswords(isLoading: Boolean) {
      _passwordsUiState.update { currentState ->
         currentState.copy(isLoadingPasswords = isLoading)
      }
   }

   private fun setLoadingCategory(isLoading: Boolean) {
      _passwordsUiState.update { currentState ->
         currentState.copy(isLoadingCategory = isLoading)
      }
   }
}