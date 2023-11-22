package adrianoqueiroz.dev.strongpass.data.model.ui_state

import adrianoqueiroz.dev.strongpass.data.model.Category
import adrianoqueiroz.dev.strongpass.data.model.Password

data class PasswordsUiState(
   val passwords: List<Password> = emptyList(),
   val category: Category = Category(),
   val isLoadingPasswords: Boolean = false,
   val isLoadingCategory: Boolean = false,
   val selectedPassword: Password = Password(),
)

