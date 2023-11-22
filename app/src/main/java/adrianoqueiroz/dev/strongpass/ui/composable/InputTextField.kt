package adrianoqueiroz.dev.strongpass.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
   value: String,
   onValueChange: (String) -> Unit,
   label: String,
   leadingIcon: @Composable (() -> Unit)? = null,
   trailingIcon: @Composable (() -> Unit)? = null,
   keyboardActions: KeyboardActions = KeyboardActions.Default,
   keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
   isError: Boolean = false,
   supportingText: @Composable (() -> Unit)? = null,
   visualTransformation: VisualTransformation = VisualTransformation.None,
   focusRequester: FocusRequester = FocusRequester.Default,
   readOnly: Boolean = false,
) {
   TextField(
      colors = TextFieldDefaults.textFieldColors(
         containerColor = MaterialTheme.colorScheme.surface,
      ),
      readOnly = readOnly,
      modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
      value = value,
      onValueChange = { onValueChange(it) },
      leadingIcon = leadingIcon,
      trailingIcon = trailingIcon,
      label = { Text(text = label) },
      keyboardActions = keyboardActions,
      keyboardOptions = keyboardOptions,
      isError = isError,
      supportingText = supportingText,
      visualTransformation = visualTransformation
   )
}

@Preview
@Composable
fun InputTextFieldPreview() {
   InputTextField(value = "", onValueChange = {}, label = "Email", leadingIcon = {
      Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
   })
}