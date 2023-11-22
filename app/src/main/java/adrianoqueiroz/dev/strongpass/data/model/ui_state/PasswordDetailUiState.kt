package adrianoqueiroz.dev.strongpass.data.model.ui_state

import adrianoqueiroz.dev.strongpass.data.model.Password
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.ui.graphics.vector.ImageVector

data class PasswordDetailUiState(
   val password: Password? = null,
   val isLoading: Boolean = false,
   var iconCopy: ImageVector = Icons.Default.ContentCopy,
   val isPasswordVisible : Boolean = false,
   val openAlertDialog: Boolean = false,
)
