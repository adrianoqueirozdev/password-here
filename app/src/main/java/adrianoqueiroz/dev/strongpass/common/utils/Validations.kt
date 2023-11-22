package adrianoqueiroz.dev.strongpass.common.utils

import adrianoqueiroz.dev.strongpass.common.ext.isValidateEmail
import adrianoqueiroz.dev.strongpass.common.ext.isValidatePassword

object Validations {
   fun errorMessageEmail(email: String): String {
      return when {
         email.isEmpty() -> return "Informe seu email"
         email.isValidateEmail().not() -> return "Email inválido"
         else -> return ""
      }
   }

   fun errorMessagePassword(password: String): String {
      return when {
         password.isEmpty() -> return "Informe sua senha"
         password.isValidatePassword().not() -> return "Senha inválida"
         else -> return ""
      }
   }

   fun errorMessageName(name: String): String {
      return when {
         name.isEmpty() -> return "Informe o nome"
         else -> return ""
      }
   }

   fun errorMessageIdUser(idUser: String): String {
      return when {
         idUser.isEmpty() -> return "Informe o id de usuário"
         else -> return ""
      }
   }

   fun errorMessageAddPassword(password: String): String {
      return when {
         password.isEmpty() -> return "Informe a senha"
         password.isValidatePassword().not() -> return "Senha deve ter no mínimo 6 caracteres"
         else -> return ""
      }
   }
}