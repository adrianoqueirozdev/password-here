package adrianoqueiroz.dev.strongpass.data.service

import adrianoqueiroz.dev.strongpass.data.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {

   val currentUserId: String
   val hasUser: Boolean

   val currentUser: Flow<User>

   suspend fun authenticate(email: String, password: String)

   suspend fun sendPasswordResetEmail(email: String)

   suspend fun signOut()
}