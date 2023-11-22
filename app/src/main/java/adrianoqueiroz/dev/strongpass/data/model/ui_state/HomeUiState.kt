package adrianoqueiroz.dev.strongpass.data.model.ui_state

import adrianoqueiroz.dev.strongpass.data.model.Category

data class HomeUiState(
   val categories: List<Category> = emptyList(),
   val isLoading: Boolean = false,
)
