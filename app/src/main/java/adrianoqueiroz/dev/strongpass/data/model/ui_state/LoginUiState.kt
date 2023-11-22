package adrianoqueiroz.dev.strongpass.data.model.ui_state

data class LoginUiState(
   val email: String = "",
   val password: String = "",
   val errorMessageEmail: String = "",
   val errorMessagePassword: String = "",
   val isLoading: Boolean = false
)
