package adrianoqueiroz.dev.strongpass.common.ext

import android.util.Patterns
private const val MIN_PASS_LENGTH = 6

fun String.idFromParameter(): String {
   return this.substring(1, this.length - 1)
}

fun String.isValidateEmail(): Boolean {
   return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidatePassword(): Boolean {
   return this.length >= MIN_PASS_LENGTH
}