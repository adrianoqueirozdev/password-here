package adrianoqueiroz.dev.strongpass.data.model.ui_state

data class RecoverPasswordUiState(
   val email: String = "",
   val errorMessageEmail: String = "",
   val isLoading: Boolean = false
)
