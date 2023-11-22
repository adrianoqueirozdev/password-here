package adrianoqueiroz.dev.strongpass.common.utils

import adrianoqueiroz.dev.strongpass.BuildConfig
import android.os.Build
import androidx.annotation.RequiresApi
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.crypto.spec.IvParameterSpec
import java.util.Base64

class EncryptData {
   companion object {
      private const val SECRETE_KEY = BuildConfig.SECRETE_KEY
      private const val algorithm = "AES/CBC/PKCS5Padding"
      private val key = SecretKeySpec(SECRETE_KEY.toByteArray(), "AES")
      private val iv = IvParameterSpec(ByteArray(16))

      @RequiresApi(Build.VERSION_CODES.O)
      fun encryptAES(data: String): String {
         val cipher = Cipher.getInstance(algorithm)
         cipher.init(Cipher.ENCRYPT_MODE, key, iv)
         val cipherText = cipher.doFinal(data.toByteArray())
         return Base64.getEncoder().encodeToString(cipherText)
      }

      @RequiresApi(Build.VERSION_CODES.O)
      fun decryptAES(data: String): String {
         val cipher = Cipher.getInstance(algorithm)
         cipher.init(Cipher.DECRYPT_MODE, key, iv)
         val plainText = cipher.doFinal(Base64.getDecoder().decode(data))
         return String(plainText)
      }
   }
}
