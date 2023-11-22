package adrianoqueiroz.dev.strongpass.ui.screens.home

import adrianoqueiroz.dev.strongpass.data.constants.Routes
import adrianoqueiroz.dev.strongpass.data.model.ui_state.HomeUiState
import adrianoqueiroz.dev.strongpass.data.service.AccountService
import adrianoqueiroz.dev.strongpass.data.service.CategoryService
import adrianoqueiroz.dev.strongpass.data.service.PasswordService
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
   private val categoryService: CategoryService,
   private val passwordService: PasswordService,
   private val accountService: AccountService,
) : ViewModel() {

   private val _homeUiState = MutableStateFlow(HomeUiState())
   val homeUiState: StateFlow<HomeUiState> = _homeUiState

   fun logout(openAndPopUp: (String, String) -> Unit) {
      viewModelScope.launch {
         try {
            accountService.signOut()
            openAndPopUp(Routes.LOGIN, Routes.HOME)
         } catch (e: Exception) {
         }
      }
   }

   fun getCategories() {
      setLoading(true)

      viewModelScope.launch {
         try {
            val categories = categoryService.getCategories()

            categories.map { category ->
               val passwords = passwordService.getPasswordsByCategory(category.id)
               category.totalPasswords = passwords.size
            }

            val sortedByCategories = categories.sortedByDescending { it.totalPasswords }

            _homeUiState.update { currentState -> currentState.copy(categories = sortedByCategories) }
            setLoading(false)
         } catch (e: Exception) {
            setLoading(false)
         }
      }
   }

   fun navigateToPasswordsScreen(openScrenn: (String) -> Unit, categoryId: String) {
      openScrenn("${Routes.PASSWORDS}?${Routes.Params.CATEGORY_ID}={$categoryId}")
   }

   private fun setLoading(isLoading: Boolean) {
      _homeUiState.update { currentState -> currentState.copy(isLoading = isLoading) }
   }
}