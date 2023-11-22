package adrianoqueiroz.dev.strongpass.data.service

import adrianoqueiroz.dev.strongpass.data.model.Password

interface PasswordService {

   suspend fun getPasswordsByCategory(categoryId: String): List<Password>

   suspend fun getPasswordById(passwordId: String): Password

   suspend fun createPassword(password: Password)

   suspend fun updatePassword(password: Password)

   suspend fun deletePassword(passwordId: String)
}