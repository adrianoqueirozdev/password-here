package adrianoqueiroz.dev.strongpass.data.model.ui_state

data class AddPasswordUiState(
   val isLoading: Boolean = false,
   val errorMessageName: String = "",
   val errorMessageIdUser: String = "",
   val errorMessagePassword: String = "",
   val name: String = "",
   val idUser: String = "",
   val password: String = "",
   val passwordCategoryId: String = "",
)
