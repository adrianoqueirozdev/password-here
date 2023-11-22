package adrianoqueiroz.dev.strongpass.data.constants


object Routes {
   const val SPLASH = "splash"
   const val HOME = "home"
   const val LOGIN = "login"
   const val RECOVER_PASSWORD = "recover_password"
   const val PASSWORDS = "passwords"
   const val ADD_PASSWORD = "add_password"
   const val EDIT_PASSWORD = "edit_password"
   const val PASSWORD_DETAIL = "password_detail"

   object Params {
      const val CATEGORY_ID = "categoryId"
      const val CATEGORY_ID_ARG = "?$CATEGORY_ID={$CATEGORY_ID}"

      const val PASSWORD_ID = "passwordId"
      const val PASSWORD_ID_ARG = "?$PASSWORD_ID={$PASSWORD_ID}"
   }
}

